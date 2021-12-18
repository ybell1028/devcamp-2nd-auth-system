package com.smilegate.user.service;

import com.smilegate.user.entity.User;
import com.smilegate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> inquiryAll() { // 페이징?
        return userRepository.findAll();
    }
}
