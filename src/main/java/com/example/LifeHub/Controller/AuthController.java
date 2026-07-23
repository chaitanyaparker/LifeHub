package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Request.LoginRequestDTO;
import com.example.LifeHub.DTO.Request.RefreshTokenRequestDTO;
import com.example.LifeHub.DTO.Request.RegisterRequestDTO;
import com.example.LifeHub.DTO.Response.LoginResponseDTO;
import com.example.LifeHub.Service.AuthService;
import com.example.LifeHub.common.APIResponse;
import com.example.LifeHub.common.APIResponseUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<Void>> Register(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO
    ) throws MessagingException, UnsupportedEncodingException {

        APIResponse<Void> response = authService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponseDTO>> Login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ){
        LoginResponseDTO response = authService.loginUser(loginRequestDTO);

        return ResponseEntity.ok(
                APIResponseUtil.success("User successfully Login", response)
        );
    }

    @GetMapping("/check-email")
    public ResponseEntity<APIResponse<Boolean>> checkEmail(
            @RequestParam String email
    ){
        boolean available = authService.isAvailability(email);

        return ResponseEntity.ok(
                APIResponseUtil.success("Email availability checked", available)
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<APIResponse<Void>> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp
    ) {
        APIResponse<Void> response = authService.verifyOtp(email, otp);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<APIResponse<LoginResponseDTO>> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO request
    ) {

        LoginResponseDTO response = authService.refreshToken(request);

        return ResponseEntity.ok(
                APIResponseUtil.success("Access token refreshed successfully", response)
        );
    }
}
