package com.example.gateway.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenPayload {
    private UUID uuid;
    private String email;

    public boolean isSame(TokenPayload anotherTokenPayload) {
        return uuid == anotherTokenPayload.getUuid() && email == anotherTokenPayload.getEmail();
    }
}
