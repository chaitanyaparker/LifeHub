package com.example.LifeHub.Security.JWT;

import com.example.LifeHub.Entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;


    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(User user){

        System.out.println("jwt token");
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getUserId())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignKey())
                .compact();

    }

    public String extractUsername(String token){

        return extractAllClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }


    private boolean isTokenExpired(String token){

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());

    }


    private Claims extractAllClaims(String token){

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }


    private SecretKey getSignKey(){

        System.out.println("This is a error");
        return Keys.hmacShaKeyFor(secretKey.getBytes());

    }

}