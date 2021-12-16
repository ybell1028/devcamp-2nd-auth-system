package com.smilegate.userserver.service;

import com.smilegate.userserver.dto.RegisterRequest;
import com.smilegate.userserver.entity.User;
import com.smilegate.userserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User register(RegisterRequest registerRequest) {
        //password 암호화 필요
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
        return userRepository.save(user);
    }
}
