package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.LoginMethod;
import com.example.LifeHub.Enums.LoginStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Instant loginAt;

    private String ipAddress;

    private String device;

    private String browser;

    private String os;

    private String location;

    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod;

    @Enumerated(EnumType.STRING)
    private LoginStatus loginStatus;
}
