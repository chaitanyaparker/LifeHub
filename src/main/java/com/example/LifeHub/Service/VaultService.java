package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Request.CreateVaultRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateVaultRequestDTO;
import com.example.LifeHub.DTO.Response.VaultResponseDTO;

import java.util.List;

public interface VaultService {

    VaultResponseDTO createVault(Long userId, CreateVaultRequestDTO request);

    VaultResponseDTO getVaultById(Long userId, Long vaultId);

    List<VaultResponseDTO> getAllVaults(Long userId);

    VaultResponseDTO updateVault(Long userId, Long vaultId, UpdateVaultRequestDTO request);

    void deleteVault(Long userId, Long vaultId);

}