package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Request.LoginRequestDTO;
import com.example.LifeHub.DTO.Request.RegisterRequestDTO;
import com.example.LifeHub.DTO.Response.LoginResponseDTO;
import com.example.LifeHub.DTO.Response.RegisterResponseDTO;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.Role;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Security.JWT.JwtService;
import com.example.LifeHub.Security.custom.CustomUserDetail;
import com.example.LifeHub.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setFirstname(registerRequestDTO.getFirstName());
        user.setLastname(registerRequestDTO.getLastName());
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(Role.User);

        User savedUser = userRepository.save(user);

        return RegisterResponseDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .message("User registered successfully")
                .build();
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        System.out.println("Step 1");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        System.out.println("Step 2");

        CustomUserDetail customUserDetail =
                (CustomUserDetail) authentication.getPrincipal();

        System.out.println("Step 3");

        User user = customUserDetail.getUser();

        System.out.println("Step 4");

        String token = jwtService.genrateToken(user);

        System.out.println("Step 5");

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .Token(token)
                .build();
    }
}