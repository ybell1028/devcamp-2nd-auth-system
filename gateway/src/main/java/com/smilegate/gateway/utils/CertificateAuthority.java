package com.smilegate.gateway.utils;

import com.smilegate.gateway.support.GatewayError;
import com.smilegate.gateway.support.GatewayException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Component
public class CertificateAuthority {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final ObjectMapper mapper = new ObjectMapper();

    public String makeToken(TokenPayload payload) {
        SignatureAlgorithm  signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        try {
            return Jwts.builder()
                    .setSubject(mapper.writeValueAsString(payload))
                    .signWith(signingKey, signatureAlgorithm)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, e);
        }
    }

    public TokenPayload decrypt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        try {
            TokenPayload payload = mapper.readValue(claims.getSubject(), TokenPayload.class);
            return payload;
        } catch (JacksonException e) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, e);
        }
    }
}
