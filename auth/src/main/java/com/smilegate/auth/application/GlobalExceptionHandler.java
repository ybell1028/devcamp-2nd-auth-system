package com.smilegate.auth.application;

import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import com.smilegate.auth.support.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthResponse<?>> UserServerException(AuthException e) {
        AuthError error = e.getError();
        log.error("AuthException : " + error.getDesc());

        return ResponseEntity
                .status(error.getStatus())
                .body(new AuthResponse<>(error.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValidException : " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse<>(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AuthResponse<?>> unknownException(Exception e) {
        log.error("UnknownException : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, AuthError.UNKNOWN_ERROR.getDesc()));
    }
}