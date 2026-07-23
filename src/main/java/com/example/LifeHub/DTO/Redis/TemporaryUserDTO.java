package com.example.LifeHub.DTO.Redis;

import lombok.Data;

@Data
public class TemporaryUserDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String otp;
}
