package com.example.LifeHub.Security.OAuth;

import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.Role;
import com.example.LifeHub.Enums.Status;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Security.JWT.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oauth2User.getAttributes();

        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");

        System.out.println("Name : " + name);
        System.out.println("Email : " + email);
        System.out.println("Picture : " + picture);

        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;

        if (optionalUser.isPresent()) {

            user = optionalUser.get();

        } else {

            user = new User();

            String[] fullName = name.split(" ", 2);

            user.setFirstname(fullName[0]);

            if (fullName.length > 1) {
                user.setLastname(fullName[1]);
            }

            user.setEmail(email);
            user.setProfilePicture(picture);
            user.setEmailVerified(true);
            user.setRole(Role.User);
            user.setStatus(Status.Online);

            user = userRepository.save(user);
        }

        String token = jwtService.generateToken(user);

        response.sendRedirect(
                "http://localhost:5173/oauth/callback?token=" + token
        );

    }
}