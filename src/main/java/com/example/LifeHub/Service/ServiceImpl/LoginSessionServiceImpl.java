package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Response.ActiveSessionResponseDTO;
import com.example.LifeHub.Entity.LoginSession;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.SessionStatus;
import com.example.LifeHub.Repository.LoginSessionRepository;
import com.example.LifeHub.Service.LoginSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginSessionServiceImpl implements LoginSessionService {

    private final LoginSessionRepository loginSessionRepository;

    @Override
    @Transactional
    public void createSession(User user, String jwtId, String sessionToken, String ipAddress, String device, String browser, String os, String location) {
        LoginSession session = LoginSession.builder()
                .user(user)
                .jwtId(jwtId)
                .sessionToken(sessionToken)
                .ipAddress(ipAddress)
                .device(device)
                .browser(browser)
                .os(os)
                .location(location)
                .status(SessionStatus.ACTIVE)
                .loginAt(Instant.now())
                .lastActivityAt(Instant.now())
                .build();

        loginSessionRepository.save(session);
    }

    @Override
    public boolean validateSession(String jwtId) {
        System.out.println("It successfully called I am validate session");

        return loginSessionRepository.findByJwtId(jwtId)
                .map(session -> session.getStatus() == SessionStatus.ACTIVE)
                .orElse(false);
    }

    @Override
    public List<ActiveSessionResponseDTO> getActiveSessions(Long userId) {

        List<LoginSession> sessions = loginSessionRepository.findAllByUserUserId(userId);

        return sessions.stream()
                .filter(session -> session.getStatus() == SessionStatus.ACTIVE)
                .map(session -> new ActiveSessionResponseDTO(
                        session.getSessionId(),
                        session.getDevice(),
                        session.getBrowser(),
                        session.getOs(),
                        session.getIpAddress(),
                        session.getLocation(),
                        session.getLastActivityAt(),
                        session.getLoginAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void logoutCurrentSession(String jwtId) {
        loginSessionRepository.findByJwtId(jwtId).ifPresent(session -> {
            session.setLogoutAt(Instant.now());
            loginSessionRepository.save(session);
        });
    }

    @Override
    @Transactional
    public void logoutAllSessions(Long userId) {
        List<LoginSession> sessions = loginSessionRepository.findAllByUserUserId(userId);
        sessions.forEach(session -> session.setLogoutAt(Instant.now()));
        loginSessionRepository.saveAll(sessions);
    }

    @Override
    @Transactional
    public void terminateSession(Long sessionId, Long userId) {
        loginSessionRepository.findById(sessionId).ifPresent(session -> {
            if (session.getUser().getUserId().equals(userId)) {
                session.setStatus(SessionStatus.EXPIRED);
                loginSessionRepository.save(session);
            }
        });
    }

    @Override
    @Transactional
    public void updateLastActivity(String jwtId) {
        loginSessionRepository.findByJwtId(jwtId).ifPresent(session -> {
            session.setLastActivityAt(Instant.now());
            loginSessionRepository.save(session);
        });
    }

    @Override
    public LoginSession findByJwtId(String jwtId) {
        return loginSessionRepository.findByJwtId(jwtId).orElse(null);
    }
}