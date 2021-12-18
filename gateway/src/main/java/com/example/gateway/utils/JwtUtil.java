package com.example.gateway.utils;

import com.example.gateway.support.GatewayError;
import com.example.gateway.support.GatewayException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Claims getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String makeToken(TokenPayload payload) {
        SignatureAlgorithm  signatureAlgorithm= SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenValidity;
        Date exp = new Date(expMillis);
        try {
            return Jwts.builder()
                    .setSubject(mapper.writeValueAsString(payload))
                    .setIssuedAt(new Date(nowMillis))
                    .setExpiration(exp)
                    .signWith(signingKey, signatureAlgorithm)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, e);
        }
    }

    public void validateToken(final String token) throws GatewayException {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, ex);
        } catch (MalformedJwtException ex) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, ex);
        } catch (ExpiredJwtException ex) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, ex);
        } catch (UnsupportedJwtException ex) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, ex);
        } catch (IllegalArgumentException ex) {
            throw new GatewayException(GatewayError.JSON_PARSE_ERROR, ex);
        }
    }
}
