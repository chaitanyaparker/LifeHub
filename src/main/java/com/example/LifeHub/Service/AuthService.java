package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Redis.TemporaryUserDTO;
import com.example.LifeHub.DTO.Request.LoginRequestDTO;
import com.example.LifeHub.DTO.Request.RefreshTokenRequestDTO;
import com.example.LifeHub.DTO.Request.RegisterRequestDTO;
import com.example.LifeHub.DTO.Response.LoginResponseDTO;
import com.example.LifeHub.common.APIResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;

public interface AuthService {

    APIResponse<Void> registerUser(RegisterRequestDTO registerRequestDTO) throws MessagingException, UnsupportedEncodingException;

    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

    Boolean isAvailability(String email);

    String generateOtp();

    APIResponse<Void> verifyOtp(String email, String userOtp);

    LoginResponseDTO refreshToken(@Valid RefreshTokenRequestDTO request);
}
