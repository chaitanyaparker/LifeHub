
package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.LoginMethod;
import com.example.LifeHub.Enums.LoginStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "login_activity",
        indexes = {
                @Index(
                        name = "idx_login_activity_user_time",
                        columnList = "user_id, login_at"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "login_at", nullable = false)
    private Instant loginAt;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    @Column(nullable = false)
    private String device;

    @Column(nullable = false, length = 150)
    private String browser;

    @Column(nullable = false, length = 100)
    private String os;

    @Column(nullable = false, length = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_method", nullable = false, length = 30)
    private LoginMethod loginMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_status", nullable = false, length = 30)
    private LoginStatus loginStatus;
}
