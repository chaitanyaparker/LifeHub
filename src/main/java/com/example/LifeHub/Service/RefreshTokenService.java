package com.example.LifeHub.Service;

import com.example.LifeHub.Entity.RefreshToken;
import com.example.LifeHub.Entity.User;

import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void deleteRefreshToken(User user);

    Optional<RefreshToken> findByToken(String token);
}