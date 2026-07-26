package com.example.LifeHub.DTO.Redis;

import lombok.Data;

import java.io.Serializable;

@Data
public class TemporaryUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String otp;
}
