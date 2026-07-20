package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Request.UserRequestDTO;
import com.example.LifeHub.Service.UserManagement;
import com.example.LifeHub.common.APIResponse;
import com.example.LifeHub.DTO.Response.UserResponseDTO;
import com.example.LifeHub.common.APIResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserManagement userManagement;


    @PostMapping
    public ResponseEntity<APIResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO userResponseDTO = userManagement.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        APIResponseUtil.success("User Created Successfully", userResponseDTO)
                );

    }

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse<UserResponseDTO>> getUserById(
            @PathVariable Long userId
    ) {

        UserResponseDTO userResponseDTO = userManagement.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        APIResponseUtil.success("User Found Successfully", userResponseDTO)
                );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<APIResponse<UserResponseDTO>> getUserByUsername(
            @PathVariable String username
    ) {

        UserResponseDTO userResponseDTO = userManagement.getUserByUsername(username);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        APIResponseUtil.success("User Found Successfully", userResponseDTO)
                );
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserResponseDTO>>> getAllUsers() {

        List<UserResponseDTO> userResponseDTO = userManagement.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        APIResponseUtil.success("Users Found Successfully", userResponseDTO)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {

        UserResponseDTO userResponseDTO = userManagement.updateUser(id, userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        APIResponseUtil.success("User Updated Successfully", userResponseDTO)
                );
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse<UserResponseDTO>> deleteUser(
            @PathVariable Long userId
    ) {

        userManagement.deleteUser(userId);
        return ResponseEntity.ok(
                APIResponseUtil.success("User Deleted Successfully", null)
        );
    }

}
