package com.smilegate.user.controller;

import com.smilegate.user.entity.vo.UserVo;
import com.smilegate.user.service.AdminService;
import com.smilegate.user.support.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/inquiry/all")
    public ResponseEntity<UserResponse<?>> inquiryAll() {
        List<UserVo> userVoList = adminService.inquiryAll().stream()
                .map(user -> UserVo.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .createdAt(user.getCreatedAt())
                        .modifiedAt(user.getModifiedAt())
                        .build())
                .collect(Collectors.toList());

        UserResponse<List<UserVo>> response = new UserResponse<>(userVoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//
//    @GetMapping("/inquiry/{uuid}")
//    public ResponseEntity<UserResponse<?>> inquiryByUUID() {
//
//    }
}
