package com.smilegate.userserver.service;

import com.smilegate.userserver.dto.RegisterRequest;
import com.smilegate.userserver.entity.User;
import com.smilegate.userserver.repository.UserRepository;
import com.smilegate.userserver.utils.CryptoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CryptoService cryptoService;

    @Transactional
    public User register(RegisterRequest registerRequest) throws Exception {
        //password 암호화 필요
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(cryptoService.encrypt(registerRequest.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public String findPassword(String encrypted) throws Exception {
        //password 암호화 필요
        return cryptoService.decrypt(encrypted);
    }
}
