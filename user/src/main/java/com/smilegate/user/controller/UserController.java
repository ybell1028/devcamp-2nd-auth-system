package com.smilegate.user.controller;

import com.smilegate.user.dto.FindPasswordRequest;
import com.smilegate.user.dto.FindPasswordResponse;
import com.smilegate.user.dto.RegisterRequest;
import com.smilegate.user.dto.RegisterResponse;
import com.smilegate.user.entity.User;
import com.smilegate.user.service.UserService;
import com.smilegate.user.support.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse<?>> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        User registeredUser = userService.register(registerRequest);
        RegisterResponse registerResponse = RegisterResponse.toDto(registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse<>(registerResponse, HttpStatus.CREATED));
    }

    // 복호화 테스트용 API
    @PostMapping("/find")
    public  ResponseEntity<UserResponse<?>> findPassword(@RequestBody FindPasswordRequest findPasswordRequest) throws Exception {
        String decrypted = userService.findPassword(findPasswordRequest.getEncrypted());
        FindPasswordResponse findPasswordResponse = FindPasswordResponse.toDto(decrypted);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse<>(findPasswordResponse));
    }
}
