package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Request.LoginRequestDTO;
import com.example.LifeHub.DTO.Request.RegisterRequestDTO;
import com.example.LifeHub.DTO.Response.LoginResponseDTO;
import com.example.LifeHub.DTO.Response.RegisterResponseDTO;
import com.example.LifeHub.Service.AuthService;
import com.example.LifeHub.common.APIResponse;
import com.example.LifeHub.common.APIResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<RegisterResponseDTO>> Register(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO
    ){
        RegisterResponseDTO registerResponseDTO = authService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                         APIResponseUtil.success("User successfully Registered", registerResponseDTO)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponseDTO>> Login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ){

        System.out.println("========== LOGIN HIT ==========");
        System.out.println(loginRequestDTO.getEmail());

        LoginResponseDTO response = authService.loginUser(loginRequestDTO);

        return ResponseEntity.ok(
                APIResponseUtil.success("User successfully Login", response)
        );
    }

}
