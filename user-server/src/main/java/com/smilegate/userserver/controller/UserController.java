package com.smilegate.userserver.controller;

import com.smilegate.userserver.dto.FindPasswordRequest;
import com.smilegate.userserver.dto.FindPasswordResponse;
import com.smilegate.userserver.dto.RegisterRequest;
import com.smilegate.userserver.dto.RegisterResponse;
import com.smilegate.userserver.entity.User;
import com.smilegate.userserver.service.UserService;
import com.smilegate.userserver.support.UserServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserServerResponse<?>> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        User registeredUser = userService.register(registerRequest);
        RegisterResponse registerResponse = RegisterResponse.toDto(registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserServerResponse<>(registerResponse));
    }

    // 복호화 테스트용 API
    @PostMapping("/find")
    public  ResponseEntity<UserServerResponse<?>> findPassword(@RequestBody FindPasswordRequest findPasswordRequest) throws Exception {
        String decrypted = userService.findPassword(findPasswordRequest.getEncrypted());
        FindPasswordResponse findPasswordResponse = FindPasswordResponse.toDto(decrypted);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserServerResponse<>(findPasswordResponse));
    }
}
