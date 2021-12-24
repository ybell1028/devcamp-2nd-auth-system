package com.smilegate.user.controller;

import com.smilegate.user.entity.User;
import com.smilegate.user.entity.vo.UserVo;
import com.smilegate.user.service.UserService;
import com.smilegate.user.support.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserAll() {
        List<UserVo> userVoList = userService.findUserAll();
        UserResponse<List<UserVo>> response = new UserResponse<>(userVoList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUuid(@RequestParam UUID uuid) {
        userService.deleteByUuid(uuid);
        UserResponse<String> response = new UserResponse<>(uuid.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping({"/{uuid}"})
    public ResponseEntity<?> getUserById(@PathVariable UUID uuid) {
        User user = userService.findByUuid(uuid);
        UserResponse<User> response = new UserResponse<>(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
