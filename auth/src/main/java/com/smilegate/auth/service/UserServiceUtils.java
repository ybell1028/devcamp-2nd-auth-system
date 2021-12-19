package com.smilegate.auth.service;

import com.smilegate.auth.repository.UserRepository;
import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {
    static void validateNotExistsUserEmail(UserRepository userRepository, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(AuthError.BAD_REQUEST, String.format("이미 존재하는 계정 (%s) 입니다.", email));
        }
    }
}
