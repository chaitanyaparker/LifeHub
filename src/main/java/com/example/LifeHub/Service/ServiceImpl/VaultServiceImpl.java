package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Request.CreateVaultRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateVaultRequestDTO;
import com.example.LifeHub.DTO.Response.VaultResponseDTO;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Entity.Vault;
import com.example.LifeHub.Enums.VaultStrength;
import com.example.LifeHub.Exception.UserNotFoundException;
import com.example.LifeHub.mapper.VaultMapper;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Repository.VaultRepository;
import com.example.LifeHub.Service.VaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaultServiceImpl implements VaultService {

    private final VaultRepository vaultRepository;
    private final UserRepository userRepository;

    @Override
    public VaultResponseDTO createVault(Long userId, CreateVaultRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Vault vault = VaultMapper.toEntity(request);

        vault.setUser(user);

        vault.setStrength(VaultStrength.Medium);

        Vault savedVault = vaultRepository.save(vault);

        return VaultMapper.toResponse(savedVault);
    }

    @Override
    public VaultResponseDTO getVaultById(Long userId, Long vaultId) {

        Vault vault = vaultRepository.findById(vaultId)
                .orElseThrow(() -> new RuntimeException("Vault not found"));

        validateOwnership(vault, userId);

        return VaultMapper.toResponse(vault);
    }

    @Override
    public List<VaultResponseDTO> getAllVaults(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        List<Vault> vaults = vaultRepository.findByUser(userId);

        return vaults.stream()
                .map(VaultMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VaultResponseDTO updateVault(Long userId, Long vaultId, UpdateVaultRequestDTO request) {

        Vault vault = vaultRepository.findById(vaultId)
                .orElseThrow(() -> new RuntimeException("Vault not found"));

        validateOwnership(vault, userId);

        VaultMapper.updateEntity(vault, request);

        Vault updatedVault = vaultRepository.save(vault);

        return VaultMapper.toResponse(updatedVault);
    }

    @Override
    public void deleteVault(Long userId, Long vaultId) {

        Vault vault = vaultRepository.findById(vaultId)
                .orElseThrow(() -> new RuntimeException("Vault not found"));

        validateOwnership(vault, userId);

        vaultRepository.delete(vault);
    }

    private void validateOwnership(Vault vault, Long userId) {
        if (!vault.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Vault not found");
        }
    }
}