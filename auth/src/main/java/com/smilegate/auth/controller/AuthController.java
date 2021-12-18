package com.smilegate.auth.controller;

import com.smilegate.auth.dto.*;
import com.smilegate.auth.entity.User;
import com.smilegate.auth.service.TokenService;
import com.smilegate.auth.service.UserService;
import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import com.smilegate.auth.support.AuthResponse;
import com.smilegate.auth.support.TokenPayload;
import com.smilegate.auth.utils.CryptoUtils;
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
    private final TokenService tokenService;
    private final CryptoUtils cryptoUtils;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse<?>> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        User registeredUser = userService.register(registerRequest);
        RegisterResponse registerResponse = RegisterResponse.toDto(registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse<>(registerResponse, HttpStatus.CREATED));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse<?>> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        User user = userService.findByEmail(loginRequest.getEmail());
        if(loginRequest.getPassword().equals(cryptoUtils.decrypt(user.getPassword()))) {
            String accessToken = tokenService.makeToken(new TokenPayload(user.getUuid(), user.getEmail()));
            LoginResponse loginResponse = new LoginResponse(accessToken);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse<>(loginResponse));
        } else {
            throw new AuthException(AuthError.BAD_REQUEST, "비밀번호가 틀립니다.");
        }
    }

    // 복호화 테스트용 API
    @PostMapping("/find")
    public  ResponseEntity<AuthResponse<?>> findPassword(@RequestBody FindPasswordRequest findPasswordRequest) throws Exception {
        String decrypted = userService.findPassword(findPasswordRequest.getEncrypted());
        FindPasswordResponse findPasswordResponse = FindPasswordResponse.toDto(decrypted);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponse<>(findPasswordResponse));
    }
}
