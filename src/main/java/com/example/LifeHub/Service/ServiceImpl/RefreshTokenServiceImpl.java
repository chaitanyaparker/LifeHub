package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.Entity.RefreshToken;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Repository.RefreshTokenRepository;
import com.example.LifeHub.Service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_VALIDITY = 7;

    @Override
    public String createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plus(REFRESH_TOKEN_VALIDITY, ChronoUnit.DAYS))
                .build();

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        return savedRefreshToken.getToken();
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh token not found"));

        if (refreshToken.getExpiry().isBefore(Instant.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}