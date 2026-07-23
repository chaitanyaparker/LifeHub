package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Request.UserRequestDTO;
import com.example.LifeHub.DTO.Response.UserResponseDTO;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.Role;
import com.example.LifeHub.Enums.Status;
import com.example.LifeHub.Exception.UserAlreadyExistException;
import com.example.LifeHub.Exception.UserNotFoundException;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Service.UserManagement;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new UserAlreadyExistException("Email already exists.");
        }

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UserAlreadyExistException("Username already exists.");
        }

        User user = toEntity(userRequestDTO);

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + id));

        return toResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + id));

        if (!user.getEmail().equals(userRequestDTO.getEmail())
                && userRepository.existsByEmail(userRequestDTO.getEmail())) {

            throw new UserAlreadyExistException("Email already exists.");
        }

        if (!user.getUsername().equals(userRequestDTO.getUsername())
                && userRepository.existsByUsername(userRequestDTO.getUsername())) {

            throw new UserAlreadyExistException("Username already exists.");
        }

        user.setFirstname(userRequestDTO.getFirstName());
        user.setLastname(userRequestDTO.getLastName());
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setBio(userRequestDTO.getBio());

        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    @Override
    public UserResponseDTO deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + id));

        userRepository.delete(user);
        return null;
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with username : " + username));

        return toResponse(user);
    }

    private User toEntity(UserRequestDTO userRequestDTO) {

        User user = new User();

        user.setFirstname(userRequestDTO.getFirstName());
        user.setLastname(userRequestDTO.getLastName());
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setBio(userRequestDTO.getBio());

        user.setRole(Role.User);
        user.setStatus(Status.Offline);
        user.setEmailVerified(false);

        return user;
    }

    private UserResponseDTO toResponse(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getUserId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}