package com.smilegate.auth.support;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthError {
    UNKNOWN_ERROR("알 수 없는 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("사용자의 입력이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    JSON_PARSE_ERROR("토큰 파싱 중 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORD_CONVERT_ERROR("비밀번호 변환 중 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String desc;
    private final HttpStatus status;

    AuthError(String desc, HttpStatus status) {
        this.desc = desc;
        this.status = status;
    }
}
