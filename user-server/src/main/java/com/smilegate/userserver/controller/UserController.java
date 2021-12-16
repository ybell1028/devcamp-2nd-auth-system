package com.smilegate.userserver.controller;

import com.smilegate.userserver.dto.FindPasswordRequest;
import com.smilegate.userserver.dto.FindPasswordResponse;
import com.smilegate.userserver.dto.RegisterRequest;
import com.smilegate.userserver.dto.RegisterResponse;
import com.smilegate.userserver.entity.User;
import com.smilegate.userserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        User registeredUser = userService.register(registerRequest);
        RegisterResponse registerResponse = RegisterResponse.toDto(registeredUser);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    // 복호화 테스트용 API
    @PostMapping("/find")
    public ResponseEntity<FindPasswordResponse> findPassword(@RequestBody FindPasswordRequest findPasswordRequest) throws Exception {
        String decrypted = userService.findPassword(findPasswordRequest.getEncrypted());
        FindPasswordResponse findPasswordResponse = FindPasswordResponse.toDto(decrypted);
        return new ResponseEntity<>(findPasswordResponse, HttpStatus.OK);
    }
}
