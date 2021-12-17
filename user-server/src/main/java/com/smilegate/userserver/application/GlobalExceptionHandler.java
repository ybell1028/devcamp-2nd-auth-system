package com.smilegate.userserver.application;

import com.smilegate.userserver.support.UserServerError;
import com.smilegate.userserver.support.UserServerException;
import com.smilegate.userserver.support.UserServerResponse;
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
    @ExceptionHandler(UserServerException.class)
    public ResponseEntity<UserServerResponse<?>> UserServerException(UserServerException e) {
        UserServerError error = e.getError();
        log.error("UserServerException : " + error.getDesc());

        return ResponseEntity
                .status(error.getStatus())
                .body(new UserServerResponse<>(error.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserServerResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValidException : " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new UserServerResponse<>(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UserServerResponse<?>> unknownException(Exception e) {
        log.error("UnknownException : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new UserServerResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, UserServerError.UNKNOWN_ERROR.getDesc()));
    }
}
