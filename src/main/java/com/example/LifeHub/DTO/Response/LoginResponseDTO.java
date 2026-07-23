package com.example.LifeHub.DTO.Response;

import com.example.LifeHub.Entity.RefreshToken;
import com.example.LifeHub.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String message;

    private Long userId;

    private String username;

    private String email;

    private Role role;

}
