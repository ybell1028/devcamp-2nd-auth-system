package com.smilegate.auth.support;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final AuthError error;

    public AuthException(AuthError error) {
        super(error.getDesc());
        this.error = error;
    }

    public AuthException(AuthError error, String message) {
        super(error.getDesc() + " : " + message);
        this.error = error;
    }

    public AuthException(AuthError error, Throwable cause) {
        super(error.getDesc(), cause);
        this.error = error;
    }
}
