package com.example.LifeHub.common;

import java.time.LocalDateTime;

public class APIResponseUtil {

    public static <T> APIResponse<T> success(String message, T data) {

        return new APIResponse<>(true,
                message,
                data,
                LocalDateTime.now()
        );

    }

    public static <T> APIResponse<T> error(String message) {

        return new APIResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
    }
}
