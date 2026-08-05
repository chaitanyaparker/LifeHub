package com.example.LifeHub.mapper;

import com.example.LifeHub.DTO.Request.CreateVaultRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateVaultRequestDTO;
import com.example.LifeHub.DTO.Response.VaultResponseDTO;
import com.example.LifeHub.Entity.Vault;
import org.springframework.stereotype.Component;

@Component
public class VaultMapper {

    public static Vault toEntity(CreateVaultRequestDTO request) {
        return Vault.builder()
                .title(request.getTitle())
                .websiteUrl(request.getWebsiteUrl())
                .loginIdentifier(request.getLoginIdentifier())
                .encryptedPassword(request.getPassword())
                .category(request.getCategory())
                .favorite(request.isFavorite())
                .build();
    }

    public static VaultResponseDTO toResponse(Vault vault) {
        return VaultResponseDTO.builder()
                .id(vault.getId())
                .title(vault.getTitle())
                .websiteUrl(vault.getWebsiteUrl())
                .loginIdentifier(vault.getLoginIdentifier())
                .category(vault.getCategory())
                .strength(vault.getStrength())
                .favorite(vault.isFavorite())
                .compromised(vault.isCompromised())
                .createdAt(vault.getCreatedAt())
                .updatedAt(vault.getUpdatedAt())
                .build();
    }

    public static void updateEntity(Vault vault, UpdateVaultRequestDTO request) {
        vault.setTitle(request.getTitle());
        vault.setWebsiteUrl(request.getWebsiteUrl());
        vault.setLoginIdentifier(request.getLoginIdentifier());
        vault.setEncryptedPassword(request.getPassword());
        vault.setCategory(request.getCategory());
        vault.setFavorite(request.isFavorite());
    }
}