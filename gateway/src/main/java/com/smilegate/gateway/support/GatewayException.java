package com.smilegate.gateway.support;

import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {
    private final GatewayError error;

    public GatewayException(GatewayError error) {
        super("GatewayExeption - " + error.getDesc());
        this.error = error;
    }

    public GatewayException(GatewayError error, String message) {
        super("GatewayExeption - " + error.getDesc() + " : " + message);
        this.error = error;
    }

    public GatewayException(GatewayError error, Throwable cause) {
        super("GatewayExeption - " + error.getDesc(), cause);
        this.error = error;
    }
}
