package com.example.LifeHub.DTO.Response;

import com.example.LifeHub.Enums.VaultCategory;
import com.example.LifeHub.Enums.VaultStrength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaultResponseDTO {

    private Long id;

    private String title;

    private String websiteUrl;

    private String loginIdentifier;

    private VaultCategory category;

    private VaultStrength strength;

    private boolean favorite;

    private boolean compromised;

    private Instant createdAt;

    private Instant updatedAt;
}
