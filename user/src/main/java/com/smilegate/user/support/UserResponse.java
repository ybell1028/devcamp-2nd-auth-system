package com.smilegate.user.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
public class UserResponse<T> {
    private T data;
    private HttpStatus status;
    private int statusCode;
    private String message;

    public UserResponse(T data) {
        this.data = data;
        this.status = HttpStatus.OK;
        this.statusCode = 200;
    }

    public UserResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
        this.statusCode = status.value();
    }

    public UserResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.statusCode = status.value();
    }
}
