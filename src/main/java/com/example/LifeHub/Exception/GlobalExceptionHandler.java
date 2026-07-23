package com.example.LifeHub.Exception;

import com.example.LifeHub.common.APIResponse;
import com.example.LifeHub.common.APIResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(APIResponseUtil.error(message));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<APIResponse<?>> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                APIResponseUtil.error(
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    APIResponseUtil.error(
                        ex.getMessage()
                    )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleException(Exception ex){

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponseUtil.error(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<?>> handleBadCredentialsException(
            BadCredentialsException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        APIResponseUtil.error("Invalid email or password")
                );
    }
}
