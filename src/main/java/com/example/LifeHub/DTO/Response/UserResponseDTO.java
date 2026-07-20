package com.example.LifeHub.DTO.Response;


import com.example.LifeHub.Enums.Role;
import com.example.LifeHub.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String profilePicture;

    private String bio;

    private Role role;

    private boolean isEmailVerified;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
