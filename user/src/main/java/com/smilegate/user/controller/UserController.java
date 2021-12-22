package com.smilegate.user.controller;

import com.smilegate.user.dto.FindPasswordRequest;
import com.smilegate.user.dto.FindPasswordResponse;
import com.smilegate.user.dto.RegisterRequest;
import com.smilegate.user.dto.RegisterResponse;
import com.smilegate.user.entity.User;
import com.smilegate.user.entity.vo.UserVo;
import com.smilegate.user.service.ConfirmationService;
import com.smilegate.user.service.UserService;
import com.smilegate.user.support.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        User registeredUser = userService.register(registerRequest);
        RegisterResponse registerResponse = RegisterResponse.toDto(registeredUser);

        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/confirmation")
    public ModelAndView viewConfirmEmail(@Valid @RequestParam UUID sign){
        userService.confirmEmail(sign);
        return new ModelAndView("login");
    }
}
