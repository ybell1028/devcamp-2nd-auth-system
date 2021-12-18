package com.smilegate.user.application;

import com.smilegate.user.support.UserError;
import com.smilegate.user.support.UserException;
import com.smilegate.user.support.UserResponse;
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
    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserResponse<?>> UserServerException(UserException e) {
        UserError error = e.getError();
        log.error("UserException : " + error.getDesc());

        return ResponseEntity
                .status(error.getStatus())
                .body(new UserResponse<>(error.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValidException : " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new UserResponse<>(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UserResponse<?>> unknownException(Exception e) {
        log.error("UnknownException : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new UserResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, UserError.UNKNOWN_ERROR.getDesc()));
    }
}
