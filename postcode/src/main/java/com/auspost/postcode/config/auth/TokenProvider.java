package com.auspost.postcode.config.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auspost.postcode.User.User;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
// responsible for generating and validation JWT
public class TokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String JWT_SECRET; // inject in env secret

    public String generateAccessToken(User user) throws ServiceValidationException {
        ValidationErrors errors = new ValidationErrors();

        if (JWT_SECRET == null) {
            errors.addError("User", "JWT_SECRET is null.");
            throw new ServiceValidationException(errors);
        }

        if (user == null) {
            errors.addError("User", "User is null.");
            throw new ServiceValidationException(errors);
        }

        if (user.getUsername() == null) {
            errors.addError("User", "Username is null.");
            throw new ServiceValidationException(errors);
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET); // uses Hash-based Message Auth Code
            return JWT.create()
                    .withSubject(user.getUsername()) // represents who the token is associated with
                    .withClaim("username", user.getUsername())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genAccessExpirationDate()) // set expiry
                    .sign(algorithm); // verify that the token is genuine
        } catch (JWTCreationException ex) {
            errors.addError("User", "Invalid token.");
            throw new ServiceValidationException(errors);
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
            // OncePerRequestFilter interface does not permit custom errors
        }
    }

    // set an expiry time for token
    private Instant genAccessExpirationDate() {
        return LocalDateTime.now(ZoneId.of("Australia/Sydney")).plusHours(2).toInstant(ZoneOffset.UTC);
    }
}
