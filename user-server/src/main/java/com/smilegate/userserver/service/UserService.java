package com.smilegate.userserver.service;

import com.fasterxml.uuid.Generators;
import com.smilegate.userserver.dto.RegisterRequest;
import com.smilegate.userserver.entity.User;
import com.smilegate.userserver.repository.UserRepository;
import com.smilegate.userserver.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
