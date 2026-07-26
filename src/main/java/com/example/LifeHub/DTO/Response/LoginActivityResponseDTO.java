package com.example.LifeHub.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginActivityResponseDTO {

    private Long loginId;

    private String loginDateTime;

    private String deviceInfo;

    private boolean currentSession;
}