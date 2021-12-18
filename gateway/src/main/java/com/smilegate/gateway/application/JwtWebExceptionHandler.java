//package com.smilegate.gateway.application;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//@Slf4j
//public class JwtWebExceptionHandler implements ErrorWebExceptionHandler {
//    private String makeJson(int errorCode) {
//        return "{\"errorCode\":" + errorCode +"}";
//    }
//    @Override
//    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
//        log.warn("JwtWebExceptionHandler : " + ex);
//        int errorCode = 999;
//        if (ex.getClass() == NullPointerException.class) {
//            errorCode = 61;
//        } else if (ex.getClass() == ExpiredJwtException.class) {
//            errorCode = 56;
//        } else if (ex.getClass() == MalformedJwtException.class || ex.getClass() == SignatureException.class || ex.getClass() == UnsupportedJwtException.class) {
//            errorCode = 55;
//        } else if (ex.getClass() == IllegalArgumentException.class) {
//            errorCode = 51;
//        }
//
//        byte[] bytes = makeJson(errorCode).getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//        return exchange.getResponse().writeWith(Flux.just(buffer));
//    }
//}
