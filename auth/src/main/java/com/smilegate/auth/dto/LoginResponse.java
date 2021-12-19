package com.smilegate.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;

    public static LoginResponse toDto(String token){
        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
