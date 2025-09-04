package com.tecno_comfenalco.pa.shared.utils.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // method for encode JWT
    public String encode(String payload) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder().subject(payload).issuedAt(now).expiration(expiration).signWith(key).compact();
    }

    // method for decode JWT
    public String decode(String token) {

        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

            // We get the subject from the payload
            return jws.getPayload().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error decoding JWT", e);
        }
    }

    // validate JWT
    public boolean validate(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // You can log the exception here if you want
            return false;
        }
    }

}
