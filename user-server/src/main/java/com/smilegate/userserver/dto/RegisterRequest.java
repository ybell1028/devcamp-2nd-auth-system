package com.smilegate.userserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

    @NotBlank(message = "비밀번호은 필수값 입니다.")
    @Length(min = 8, max = 24, message = "비밀번호은 최소 8자, 최대 24자 까지 입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,24}", message = "비밀번호는 최소 8자, 최대 24자 까지이며 영문, 숫자, 특수문자가 적어도 1개 이상 포함되어야 합니다.")
    private String password;
}