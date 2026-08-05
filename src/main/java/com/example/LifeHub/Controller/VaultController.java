package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Request.CreateVaultRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateVaultRequestDTO;
import com.example.LifeHub.DTO.Response.VaultResponseDTO;
import com.example.LifeHub.Service.VaultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/vaults")
@RequiredArgsConstructor
public class VaultController {

    private final VaultService vaultService;

    @PostMapping
    public ResponseEntity<VaultResponseDTO> createVault(
            @PathVariable Long userId,
            @Valid @RequestBody CreateVaultRequestDTO request) {

        VaultResponseDTO response = vaultService.createVault(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{vaultId}")
    public ResponseEntity<VaultResponseDTO> getVaultById(
            @PathVariable Long userId,
            @PathVariable Long vaultId) {

        VaultResponseDTO response = vaultService.getVaultById(userId, vaultId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VaultResponseDTO>> getAllVaults(
            @PathVariable Long userId) {

        List<VaultResponseDTO> response = vaultService.getAllVaults(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{vaultId}")
    public ResponseEntity<VaultResponseDTO> updateVault(
            @PathVariable Long userId,
            @PathVariable Long vaultId,
            @Valid @RequestBody UpdateVaultRequestDTO request) {

        VaultResponseDTO response = vaultService.updateVault(userId, vaultId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{vaultId}")
    public ResponseEntity<Void> deleteVault(
            @PathVariable Long userId,
            @PathVariable Long vaultId) {

        vaultService.deleteVault(userId, vaultId);
        return ResponseEntity.noContent().build();
    }
}