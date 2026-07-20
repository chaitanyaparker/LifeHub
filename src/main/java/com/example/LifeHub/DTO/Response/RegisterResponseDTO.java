package com.example.LifeHub.DTO.Response;

import com.example.LifeHub.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {

    private String message;

    private Long id;

    private String username;

    private String email;

    private Role role;
}
