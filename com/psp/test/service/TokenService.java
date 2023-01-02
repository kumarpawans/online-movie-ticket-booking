package com.psp.test.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
    private static final String ISSUER = "Example App";
    private static final int TOKEN_DURATION_MINUTES = 30;
    private static Key SECRET_KEY = null;

    public static String generateToken(String subject) {
        Instant now = Instant.now();
        Date expirationTime = Date.from(now.plusSeconds(TOKEN_DURATION_MINUTES * 60));

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(ISSUER)
                .setIssuedAt(Date.from(now))
                .setExpiration(expirationTime)
                //.signWith(SECRET_KEY) // generate key to sign
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
