package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Redis.TemporaryUserDTO;
import com.example.LifeHub.DTO.Request.LoginRequestDTO;
import com.example.LifeHub.DTO.Request.RefreshTokenRequestDTO;
import com.example.LifeHub.DTO.Request.RegisterRequestDTO;
import com.example.LifeHub.DTO.Response.LoginResponseDTO;
import com.example.LifeHub.Entity.RefreshToken;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Security.JWT.JwtService;
import com.example.LifeHub.Security.custom.CustomUserDetail;
import com.example.LifeHub.Service.AuthService;
import com.example.LifeHub.Service.EmailSender;
import com.example.LifeHub.Service.RefreshTokenService;
import com.example.LifeHub.common.APIResponse;
import com.example.LifeHub.redis.RedisService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final EmailSender emailSender;
    private final RefreshTokenService refreshTokenService;

    @Override
    public APIResponse<Void> registerUser(RegisterRequestDTO registerRequestDTO) throws MessagingException, UnsupportedEncodingException {

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String otp = generateOtp();

        TemporaryUserDTO user = new TemporaryUserDTO();
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setOtp(otp);

        redisService.saveTemporaryUser(user);

        emailSender.sendOtp(
                user.getEmail(),
                "Send Otp",
                "Your Otp code is " + otp
        );


        return APIResponse.<Void>builder()
                .success(true)
                .message("OTP sent successfully")
                .data(null)
                .build();
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        CustomUserDetail customUserDetail =
                (CustomUserDetail) authentication.getPrincipal();

        User user = customUserDetail.getUser();

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .refreshToken(refreshTokenService.createRefreshToken(user))
                .role(user.getRole())
                .accessToken(token)
                .build();
    }

    @Override
    public Boolean isAvailability(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public APIResponse<Void> verifyOtp(String email, String userOtp) {

        TemporaryUserDTO tempUser = redisService.getTemporaryUser(email);

        if (tempUser == null) {
            return APIResponse.<Void>builder()
                    .success(false)
                    .message("OTP expired")
                    .data(null)
                    .build();
        }


        if (!tempUser.getOtp().equals(userOtp)) {
            return APIResponse.<Void>builder()
                    .success(false)
                    .message("Incorrect OTP")
                    .data(null)
                    .build();
        }


        User user = new User();

        user.setFirstname(tempUser.getFirstName());
        user.setLastname(tempUser.getLastName());
        user.setUsername(tempUser.getUsername());
        user.setEmail(tempUser.getEmail());
        user.setPassword(tempUser.getPassword());

        userRepository.save(user);

        redisService.deleteTemporaryUser(email);

        return APIResponse.<Void>builder()
                .success(true)
                .message("Registration successful")
                .data(null)
                .build();
    }

    @Override
    public LoginResponseDTO refreshToken(RefreshTokenRequestDTO request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Access token refreshed successfully")
                .build();
    }
}