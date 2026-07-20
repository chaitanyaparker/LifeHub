package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Request.LoginRequestDTO;
import com.example.LifeHub.DTO.Request.RegisterRequestDTO;
import com.example.LifeHub.DTO.Response.LoginResponseDTO;
import com.example.LifeHub.DTO.Response.RegisterResponseDTO;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.Role;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new RuntimeException("Email is already exist");
        }

        if(userRepository.existsByUsername(registerRequestDTO.getUsername())){
            throw new RuntimeException("Username is already exist");
        }


        User user = new User();

        user.setFirstname(registerRequestDTO.getFirstName());
        user.setLastname(registerRequestDTO.getLastName());
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());

        user.setPassword(
                passwordEncoder.encode(registerRequestDTO.getPassword())
        );


        user.setRole(Role.valueOf("USER"));


        User result = userRepository.save(user);


        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();

        registerResponseDTO.setId(result.getId());
        registerResponseDTO.setEmail(result.getEmail());
        registerResponseDTO.setUsername(result.getUsername());
        registerResponseDTO.setRole(result.getRole());
        registerResponseDTO.setMessage("User successfully registered");


        return registerResponseDTO;
    }



    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {


        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if(!passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword()
        )){
            throw new RuntimeException("Invalid password");
        }


        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        loginResponseDTO.setMessage("Login Successful");
        loginResponseDTO.setUserId(user.getId());
        loginResponseDTO.setUsername(user.getUsername());
        loginResponseDTO.setEmail(user.getEmail());
        loginResponseDTO.setRole(user.getRole());

        loginResponseDTO.setToken(null);


        return loginResponseDTO;
    }
}