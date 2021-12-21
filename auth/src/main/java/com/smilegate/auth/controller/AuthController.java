package com.smilegate.auth.controller;

import com.smilegate.auth.dto.*;
import com.smilegate.auth.entity.User;
import com.smilegate.auth.service.ConfirmationService;
import com.smilegate.auth.service.UserService;
import com.smilegate.auth.support.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ConfirmationService confirmationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        User registeredUser = userService.register(registerRequest);
        confirmationService.sendConfirm(registeredUser.getUuid(), registeredUser.getEmail());
        RegisterResponse registerResponse = RegisterResponse.toDto(registeredUser);

        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        LoginResponse loginResponse = LoginResponse.toDto(token);

        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }

    // 복호화 테스트용 API
    @PostMapping("/find")
    public  ResponseEntity<AuthResponse<?>> findPassword(@RequestBody FindPasswordRequest findPasswordRequest) throws Exception {
        String decrypted = userService.findPassword(findPasswordRequest.getEncrypted());
        FindPasswordResponse findPasswordResponse = FindPasswordResponse.toDto(decrypted);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponse<>(findPasswordResponse));
    }

    @GetMapping("/confirmation")
    public ModelAndView viewConfirmEmail(@Valid @RequestParam UUID sign){
        userService.confirmEmail(sign);
        return new ModelAndView("login");
    }
}
