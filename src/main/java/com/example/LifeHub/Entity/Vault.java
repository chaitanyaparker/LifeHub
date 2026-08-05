package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.VaultCategory;
import com.example.LifeHub.Enums.VaultStrength;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String websiteUrl;

    private String loginIdentifier;

    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    private VaultCategory category;

    @Enumerated(EnumType.STRING)
    private VaultStrength strength;

    @Builder.Default
    private boolean favorite = false;

    @Builder.Default
    private boolean compromised = false;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
