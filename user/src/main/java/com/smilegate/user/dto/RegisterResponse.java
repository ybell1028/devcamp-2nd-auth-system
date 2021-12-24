package com.smilegate.user.dto;

import com.smilegate.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private String name;
    private String email;

    public static RegisterResponse toDto(User user) {
        return RegisterResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
