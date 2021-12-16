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
    @Length(max = 16, message = "이름은 최대 16자 까지 입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Length(max = 32, message = "이메일은 최대 32자 까지 입니다.")
    private String email;

    @NotBlank(message = "암호화 된 비밀번호은 필수값 입니다.")
    @Length(max = 64, message = "암호화 된 비밀번호은 최대 64자 까지 입니다.")
    private String password;
}