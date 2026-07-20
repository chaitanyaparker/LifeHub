package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Request.UserRequestDTO;
import com.example.LifeHub.DTO.Response.UserResponseDTO;

import java.util.List;

public interface UserManagement {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long UserId);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    UserResponseDTO deleteUser(Long UserId);

    UserResponseDTO getUserByUsername(String Username);
}
