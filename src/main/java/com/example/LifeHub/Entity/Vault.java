
package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.VaultCategory;
import com.example.LifeHub.Enums.VaultStrength;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "vault",
        indexes = {
                @Index(name = "idx_vault_user_id", columnList = "user_id")
        }
)
@Getter
@Setter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank
    @Size(max = 2048)
    @Column(nullable = false, length = 2048)
    private String websiteUrl;

    @NotBlank
    @Size(max = 320)
    @Column(nullable = false, length = 320)
    private String loginIdentifier;

    @NotBlank
    @Column(nullable = false, length = 2048)
    private String encryptedPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private VaultCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private VaultStrength strength;

    @Builder.Default
    @Column(nullable = false)
    private boolean favorite = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean compromised = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
}
