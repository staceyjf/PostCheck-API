package com.auspost.postcode.config.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auspost.postcode.User.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
// responsible for generating and validation JWT
public class TokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String JWT_SECRET; // inject in env secret

    public String generateAccessToken(User user) {
        if (JWT_SECRET == null) {
            throw new IllegalArgumentException("JWT_SECRET is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getUsername() == null) {
            throw new IllegalArgumentException("Username is null");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET); // uses Hash-based Message Auth Code
            return JWT.create()
                    .withSubject(user.getUsername()) // represents who the token is associated with
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(genAccessExpirationDate()) // set expiry
                    .sign(algorithm); // verify that the token is genuine
        } catch (JWTCreationException ex) {
            throw new JWTCreationException("Error while generating token", ex);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build() // return a verifier
                    .verify(token)
                    .getSubject(); // return the subject which is our username
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }

    // set an expiry time for token
    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
