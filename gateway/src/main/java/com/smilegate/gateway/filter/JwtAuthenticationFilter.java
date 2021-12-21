package com.smilegate.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.gateway.support.GatewayError;
import com.smilegate.gateway.support.GatewayException;
import com.smilegate.gateway.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Bean
    public ErrorWebExceptionHandler jwtWebExceptionHandler() {
        return new JwtUtils.JwtWebExceptionHandler();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            List<String> list = request.getHeaders().get("x-access-token");
            String token = Objects.requireNonNull(list).get(0);
            
            //to-be token payload 추출해서 request json에 추가
            Map<String, Object> payload = JwtUtils.getPayloadFromToken(token, objectMapper);

            try {
                String newRequestBody = objectMapper.writeValueAsString(payload); // non-blocking으로 개선 필요
                ModifyRequestBodyGatewayFilterFactory.Config modifyRequestConfig = new ModifyRequestBodyGatewayFilterFactory.Config()
                        .setContentType(ContentType.APPLICATION_JSON.getMimeType())
                        .setRewriteFunction(String.class, String.class, (webExchange, originalRequestBody) -> Mono.just(newRequestBody));

                return new ModifyRequestBodyGatewayFilterFactory().apply(modifyRequestConfig).filter(exchange, chain);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new GatewayException(GatewayError.UNKNOWN_ERROR, "objectMapper json 변환 중 에러");
            }
            // return chain.filter(exchange);
        });
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private String name;
        private String message;
    }
}