package com.smilegate.userserver.service;

import com.smilegate.userserver.repository.UserRepository;
import com.smilegate.userserver.support.UserServerError;
import com.smilegate.userserver.support.UserServerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {
    static void validateNotExistsUserEmail(UserRepository userRepository, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserServerException(UserServerError.BAD_REQUEST, String.format("이미 존재하는 이메일 (%s) 입니다.", email));
        }
    }
}
