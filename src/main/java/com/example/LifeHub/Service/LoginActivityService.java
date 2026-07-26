package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Response.LoginActivityResponseDTO;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.LoginMethod;
import com.example.LifeHub.Enums.LoginStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface LoginActivityService {

    void saveLoginActivity(User user,
                           LoginMethod loginMethod,
                           LoginStatus loginStatus,
                           HttpServletRequest request);

    List<LoginActivityResponseDTO> getLoginHistory(User user);
}
