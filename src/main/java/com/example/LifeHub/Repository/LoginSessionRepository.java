package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.LoginSession;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {

    List<LoginSession> findAllByUserUserId(Long userId);

    Optional<LoginSession> findByJwtId(String jwtId);
}