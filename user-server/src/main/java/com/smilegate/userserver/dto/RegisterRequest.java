package com.smilegate.userserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "이름은 필수값 입니다.")
    @Length(max = 20, message = "이름은 최대 20자 까지 입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Length(max = 20, message = "이름은 최대 40자 까지 입니다.")
    private String email;

    @NotBlank(message = "비밀번호은 필수값 입니다.")
    @Length(max = 20, message = "비밀번호은 최대 20자 까지 입니다.")
    private String password;
}