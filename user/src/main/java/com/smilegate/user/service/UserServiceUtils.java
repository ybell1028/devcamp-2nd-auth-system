package com.smilegate.user.service;

import com.smilegate.user.repository.UserRepository;
import com.smilegate.user.support.UserError;
import com.smilegate.user.support.UserException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {
    static void validateNotExistsUserEmail(UserRepository userRepository, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserException(UserError.BAD_REQUEST, String.format("이미 존재하는 이메일 (%s) 입니다.", email));
        }
    }
}
