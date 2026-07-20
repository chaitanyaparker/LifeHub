package com.example.LifeHub.common;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class APIResponse<T>{

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;

    public APIResponse(boolean success, String message, T data, LocalDateTime now) {

    }
}
