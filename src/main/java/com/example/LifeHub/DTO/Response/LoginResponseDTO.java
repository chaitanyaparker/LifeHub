package com.example.LifeHub.DTO.Response;

import com.example.LifeHub.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String Token;

    private String message;

    private Long userId;

    private String username;

    private String email;

    private Role role;

}
