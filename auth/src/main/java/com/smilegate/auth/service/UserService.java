package com.smilegate.auth.service;

import com.smilegate.auth.entity.User;
import com.smilegate.auth.repository.UserRepository;
import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthError.BAD_REQUEST, "존재하지 않는 계정입니다."));
    }
}
