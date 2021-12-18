package com.smilegate.gateway.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.gateway.support.GatewayError;
import com.smilegate.gateway.support.GatewayException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    private static String jwtSecret;

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public static Map<String, Object> getPayloadFromToken(String token, ObjectMapper objectMapper) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
        try {
            Map<String, Object> payload = objectMapper.readValue(claims.getSubject(), Map.class);
            return payload;
        } catch (JacksonException e) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, e.getMessage());
        }
    }

    public static class JwtWebExceptionHandler implements ErrorWebExceptionHandler {
        // ModifyResponseBodyGatewayFilterFactory로 변경할 것
        private String makeJson(GatewayError gatewayError, String message) {
            return String.format("{ \"status\" : \"%s\", \"statusCode\" : \"%s\", \"message\" : \"%s\"" ,
                    gatewayError.getStatus(),
                    gatewayError.getStatus().value(),
                    gatewayError.getDesc() + " : " + message
            );
        }
        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            log.warn("JwtWebExceptionHandler : " + ex);
            String message = "";
            if (ex.getClass() == ExpiredJwtException.class) {
                message = "만료된 토큰입니다.";
            } else if (ex.getClass() == MalformedJwtException.class ||
                    ex.getClass() == SignatureException.class ||
                    ex.getClass() == UnsupportedJwtException.class) {
                message = "올바르지 않은 형식의 토큰입니다.";
            } else if (ex.getClass() == IllegalArgumentException.class) {
                message = "헤더에 토큰이 포함되지 않았습니다.";
            }

            byte[] bytes = makeJson(GatewayError.JWT_AUTHENTICATION, message).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
    }
}
