package com.example.LifeHub.Security.JWT;

import com.example.LifeHub.Security.Service.CustomUserDetailService;
import com.example.LifeHub.Service.LoginSessionService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final LoginSessionService loginSessionService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractToken(request);

        // Temporary debugging logs. Never print the actual token.
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println(
                "Authorization header present: "
                        + (request.getHeader("Authorization") != null)
        );

        Cookie[] cookies = request.getCookies();

        boolean cookiePresent = cookies != null
                && Arrays.stream(cookies)
                .anyMatch(cookie ->
                        "accessToken".equals(cookie.getName()));

        System.out.println("Access token cookie present: " + cookiePresent);
        System.out.println(
                "JWT extracted: " + (token != null && !token.isBlank())
        );

        // No token: let Spring Security handle endpoint authorization.
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String email = jwtService.extractUsername(token);
            String jwtId = jwtService.extractJti(token);

            if (email == null || email.isBlank()
                    || jwtId == null || jwtId.isBlank()) {
                SecurityContextHolder.clearContext();
                unauthorized(response);
                return;
            }

            // Authenticate only if no authentication exists yet.
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        customUserDetailService.loadUserByUsername(email);

                boolean validToken =
                        jwtService.isTokenValid(token, userDetails);

                boolean validSession =
                        loginSessionService.validateSession(jwtId);

                // Debugging results without exposing token/session identifiers.
                System.out.println("JWT valid: " + validToken);
                System.out.println("Session valid: " + validSession);

                if (!validToken || !validSession) {
                    SecurityContextHolder.clearContext();
                    unauthorized(response);
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                loginSessionService.updateLastActivity(jwtId);
            }

            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            System.err.println(
                    "JWT validation failed: " + ex.getClass().getSimpleName()
            );
            unauthorized(response);

        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
            SecurityContextHolder.clearContext();
            System.err.println("JWT user not found");
            unauthorized(response);
        }
    }

    private String extractToken(HttpServletRequest request) {

        // First preference: Authorization: Bearer <token>
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String bearerToken = authHeader.substring(7).trim();

            if (!bearerToken.isBlank()) {
                return bearerToken;
            }
        }

        // Fallback: accessToken cookie
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    String cookieToken = cookie.getValue();

                    if (cookieToken != null && !cookieToken.isBlank()) {
                        return cookieToken.trim();
                    }
                }
            }
        }

        return null;
    }

    private void unauthorized(HttpServletResponse response)
            throws IOException {

        if (!response.isCommitted()) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired authentication token"
            );
        }
    }
}
