package com.smilegate.auth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    //스프링 시큐리티 사용 고려
    ASSOCIATE("ROLE_ASSOCIATE", "준회원"), // 이메일 인증 받지 않음
    REGULAR("ROLE_REGULAR", "정회원"), // 이메일 인증 받음
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
