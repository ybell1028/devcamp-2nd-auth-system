package com.smilegate.user.service;

import com.smilegate.user.entity.User;
import com.smilegate.user.entity.vo.UserVo;
import com.smilegate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserVo> inquiryAll() { // 페이징?
        return userRepository.findAll().stream()
                .map(user -> UserVo.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .createdAt(user.getCreatedAt())
                        .modifiedAt(user.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
