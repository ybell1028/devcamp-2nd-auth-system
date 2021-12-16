package com.smilegate.userserver.dto;

import com.smilegate.userserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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
