package com.smilegate.user.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.user.support.UserError;
import com.smilegate.user.support.UserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
public class CertificateAuthority {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TokenPayload decrypt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();

        try {
            TokenPayload payload = objectMapper.readValue(claims.getSubject(), TokenPayload.class);
            return payload;
        } catch (JacksonException e) {
            throw new UserException(UserError.JSON_PARSE_ERROR, e.getMessage());
        }
    }
}
