package com.example.LifeHub.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;
}