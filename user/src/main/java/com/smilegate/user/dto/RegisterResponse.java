package com.smilegate.user.dto;

import com.smilegate.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
