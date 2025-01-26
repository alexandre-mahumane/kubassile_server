package com.kubassile.kubassile.service;

import com.kubassile.kubassile.domain.user.Users;

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

    public String generateToken(Users data) {
        return JWT.create()
                .withIssuer("kubassile-securtiy")
                .withSubject(data.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(3600L))
                .sign(Algorithm.HMAC256(secret));
    }

    public String decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("kubassile-securtiy")
                .build()
                .verify(token).getSubject();
    }
}
