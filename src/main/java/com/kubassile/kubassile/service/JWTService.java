package com.kubassile.kubassile.service;

import com.kubassile.kubassile.domain.user.Users;
import com.kubassile.kubassile.exceptions.ForbiddenException;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTService {

    @Value("${jwt.secret.api}")
    private String secret;

    Instant now = Instant.now();

    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15;
    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 15;

    public String generateToken(Users data) {
        return JWT.create()
                .withIssuer("kubassile-securtiy")
                .withSubject(data.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(ACCESS_TOKEN_VALIDITY))
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(Users data) {
        return JWT.create()
                .withIssuer("kubassile-securtiy")
                .withSubject(data.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(REFRESH_TOKEN_VALIDITY))
                .sign(Algorithm.HMAC256(secret));
    }

    public String decodeToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("kubassile-securtiy")
                    .build()
                    .verify(token).getSubject();

        } catch (Exception e) {
            throw new ForbiddenException("Invalid or expired token");
        }
    }
}
