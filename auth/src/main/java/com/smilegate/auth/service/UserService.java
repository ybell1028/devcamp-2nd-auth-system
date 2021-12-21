package com.smilegate.auth.service;

import com.smilegate.auth.dto.LoginRequest;
import com.smilegate.auth.entity.User;
import com.smilegate.auth.repository.UserRepository;
import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import com.smilegate.auth.support.TokenPayload;
import com.smilegate.auth.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CryptoUtils cryptoUtils;
    private final TokenService tokenService;

    @Transactional
    public String login(LoginRequest loginRequest) {
        User user = findByEmail(loginRequest.getEmail());
        String decrypted = "";
        try {
            decrypted = cryptoUtils.decrypt(user.getPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthException(AuthError.PASSWORD_CONVERT_ERROR, ex.getCause());
        }

        if(loginRequest.getPassword().equals(decrypted)) {
            return tokenService.makeToken(new TokenPayload(user.getUuid(), user.getEmail()));
        } else {
            throw new AuthException(AuthError.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthError.BAD_REQUEST, "존재하지 않는 계정입니다."));
    }
}
