package com.example.LifeHub.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  unique = true)
    private String token;

    @OneToOne
    @JoinColumn(referencedColumnName = "userId")
    private User user;

    private Instant expiry;
}
