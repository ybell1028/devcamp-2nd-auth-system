package com.smilegate.auth.controller;

import com.smilegate.auth.dto.LoginRequest;
import com.smilegate.auth.dto.LoginResponse;
import com.smilegate.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        LoginResponse loginResponse = LoginResponse.toDto(token);

        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }
}
