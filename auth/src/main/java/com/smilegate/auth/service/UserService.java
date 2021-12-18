package com.smilegate.auth.service;

import com.fasterxml.uuid.Generators;
import com.smilegate.auth.dto.RegisterRequest;
import com.smilegate.auth.entity.User;
import com.smilegate.auth.repository.UserRepository;
import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import com.smilegate.auth.utils.CryptoUtils;
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

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthError.BAD_REQUEST, "존재하지 않는 계정입니다."));
    }

    @Transactional
    public String findPassword(String encrypted) throws Exception {
        //password 암호화 필요
        return cryptoUtils.decrypt(encrypted);
    }
}
