package com.smilegate.user.service;

import com.fasterxml.uuid.Generators;
import com.smilegate.user.dto.RegisterRequest;
import com.smilegate.user.entity.User;
import com.smilegate.user.repository.UserRepository;
import com.smilegate.user.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CryptoUtils cryptoUtils;

    @Transactional
    public User register(RegisterRequest registerRequest) throws Exception {
        UserServiceUtils.validateNotExistsUserEmail(userRepository, registerRequest.getEmail());
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .uuid(Generators.timeBasedGenerator().generate())
                .password(cryptoUtils.encrypt(registerRequest.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public String findPassword(String encrypted) throws Exception {
        //password 암호화 필요
        return cryptoUtils.decrypt(encrypted);
    }
}
