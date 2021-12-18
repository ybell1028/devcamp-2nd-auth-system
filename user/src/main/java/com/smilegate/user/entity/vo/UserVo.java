package com.smilegate.user.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String name;
    private String email;
    private LocalDateTime createdAt; // 포맷 변경?
    private LocalDateTime modifiedAt;
}
