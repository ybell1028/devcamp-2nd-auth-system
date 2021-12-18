//package com.example.gateway.filter;
//
//import com.example.gateway.support.GatewayException;
//import com.example.gateway.utils.JwtUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHeaders;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Objects;
//
//@Slf4j
//@Component
//public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    private JwtUtil jwtUtil;
//
//    public JwtAuthenticationFilter() {
//        super(Config.class);
//    }
//
//    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//        super(Config.class);
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//
//            // Request Header 에 token 이 존재하지 않을 때
//            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
//                return handleUnAuthorized(exchange); // 401 Error
//            }
//
//            // Request Header 에서 token 문자열 받아오기
//            List<String> token = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
//            String tokenString = Objects.requireNonNull(token).get(0);
//
//            try {
//                // 토큰 검증
//                jwtUtil.validateToken(tokenString);
//            } catch (GatewayException e) {
//                e.printStackTrace();
//                return handleUnAuthorized(exchange);
//            }
//
//            return chain.filter(exchange); // 토큰이 일치할 때
//        });
//    }
//
//    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
//
//    public static class Config {
//    }
//}