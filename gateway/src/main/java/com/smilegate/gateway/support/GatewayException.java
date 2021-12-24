package com.smilegate.gateway.support;

import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {
    private final GatewayError error;

    public GatewayException(GatewayError error) {
        super(error.getDesc());
        this.error = error;
    }

    public GatewayException(GatewayError error, String message) {
        super(error.getDesc() + " : " + message);
        this.error = error;
    }

    public GatewayException(GatewayError error, Throwable cause) {
        super(error.getDesc(), cause);
        this.error = error;
    }
}
