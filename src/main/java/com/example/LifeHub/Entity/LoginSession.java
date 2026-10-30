
package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "login_sessions",
        indexes = {
                @Index(
                        name = "idx_login_session_user_status",
                        columnList = "user_id, status"
                ),
                @Index(
                        name = "idx_login_session_user_expiry",
                        columnList = "user_id, expires_at"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "sessionToken"})
public class LoginSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(nullable = false, unique = true, length = 128)
    @ToString.Exclude
    private String sessionToken;

    @Column(nullable = false, unique = true, length = 100)
    private String jwtId;

    @Column(nullable = false)
    private String device;

    @Column(nullable = false, length = 150)
    private String browser;

    @Column(nullable = false, length = 100)
    private String os;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    @Column(nullable = false, length = 150)
    private String location;

    @CreationTimestamp
    @Column(name = "login_at", nullable = false, updatable = false)
    private Instant loginAt;

    @Column(name = "last_activity_at", nullable = false)
    private Instant lastActivityAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "logout_at")
    private Instant logoutAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SessionStatus status;
}
