package com.smilegate.user.support;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final UserError error;

    public UserException(UserError error) {
        super(error.getDesc());
        this.error = error;
    }

    public UserException(UserError error, String message) {
        super(error.getDesc() + " : " + message);
        this.error = error;
    }

    public UserException(UserError error, Throwable cause) {
        super(error.getDesc(), cause);
        this.error = error;
    }
}
