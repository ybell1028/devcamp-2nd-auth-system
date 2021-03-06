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
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {
    private static String jwtSecret;

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, e);
        }
    }

    public static class JwtWebExceptionHandler implements ErrorWebExceptionHandler {
        private String makeJson(GatewayError gatewayError, String message) {
            return String.format("{ \"status\" : \"%s\", \"statusCode\" : \"%s\", \"message\" : \"%s\"" ,
                    gatewayError.getStatus(),
                    gatewayError.getStatus().value(),
                    gatewayError.getDesc() + " : " + message + "\" }"
            );
        }


        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            log.warn("JwtWebExceptionHandler : " + ex);

            String message = "";
            if (ex.getClass() == NullPointerException.class) {
                message = "x-access-token ????????? ???????????? ???????????????.";
            }
            else if (ex.getClass() == ExpiredJwtException.class) {
                message = "????????? ???????????????.";
            } else if (ex.getClass() == MalformedJwtException.class ||
                    ex.getClass() == SignatureException.class ||
                    ex.getClass() == UnsupportedJwtException.class) {
                message = "???????????? ?????? ????????? ???????????????.";
            } else if (ex.getClass() == IllegalArgumentException.class) {
                message = "????????? ????????? ???????????? ???????????????.";
            }

            byte[] bytes = makeJson(GatewayError.JWT_AUTHENTICATION, message).getBytes(StandardCharsets.UTF_8);
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
    }
}
