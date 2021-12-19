package com.smilegate.user.support;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final UserError error;

    public UserException(UserError error) {
        super("UserExeption - " + error.getDesc());
        this.error = error;
    }

    public UserException(UserError error, String message) {
        super("UserExeption - " + error.getDesc() + " : " + message);
        this.error = error;
    }

    public UserException(UserError error, Throwable cause) {
        super("UserExeption - " + error.getDesc(), cause);
        this.error = error;
    }
}
