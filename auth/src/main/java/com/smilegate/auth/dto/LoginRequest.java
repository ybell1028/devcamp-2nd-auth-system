package com.smilegate.auth.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter

public class LoginRequest {
    @NotBlank(message = "이메일은 필수값 입니다.")
    @Length(max = 32, message = "이메일은 최대 32자 까지 입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호은 필수값 입니다.")
    @Length(min = 8, max = 24, message = "비밀번호은 최소 8자, 최대 24자 까지 입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,24}", message = "비밀번호는 영문, 숫자, 특수문자가 적어도 1개 이상 포함되어야 합니다.")
    private String password;
}
