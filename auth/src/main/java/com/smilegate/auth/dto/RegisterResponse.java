package com.smilegate.auth.dto;

import com.smilegate.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
