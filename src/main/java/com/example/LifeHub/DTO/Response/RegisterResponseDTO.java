package com.example.LifeHub.DTO.Response;

import com.example.LifeHub.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponseDTO {

    private String message;

    private Long id;

    private String username;

    private String email;

    private Role role;
}
