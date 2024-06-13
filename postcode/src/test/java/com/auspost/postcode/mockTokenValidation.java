package com.auspost.postcode;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import org.springframework.security.core.Authentication;

import com.auspost.postcode.User.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class mockTokenValidation {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "Ilovepostcodes";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    // this create a JWT for a given username and add it to the authorization header
    // of the response
    public static String createToken(User user) {
        String jwt = JWT.create()
                .withSubject(user
                        .getUsername())
                .withClaim("username",
                        user.getUsername())
                .withExpiresAt(genAccessExpirationDate())
                .sign(Algorithm.HMAC256(SECRET));

        return jwt;
    }

    // public static String validateToken(String token) {
    // String jwt = JWT.require(Algorithm.HMAC256(
    // SECRET))
    // .build().verify(token).getSubject();
    // return jwt;
    // }

    // set an expiry time for token
    private static Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
