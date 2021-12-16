package com.smilegate.userserver.support;

import lombok.Getter;

@Getter
public class UserServerException extends RuntimeException {
    private final UserServerError error;

    public UserServerException(UserServerError error) {
        super(error.getDesc());
        this.error = error;
    }

    public UserServerException(UserServerError error, String message) {
        super(error.getDesc() + " : " + message);
        this.error = error;
    }

    public UserServerException(UserServerError error, Throwable cause) {
        super(error.getDesc(), cause);
        this.error = error;
    }
}
