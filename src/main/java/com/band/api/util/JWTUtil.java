package com.band.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    public Algorithm getAlgorithm(String token_secret) {
        return Algorithm.HMAC256(token_secret);
    }

    public String create(Integer id, Algorithm algorithm) {
        return JWT.create()
                .withClaim("id", id)
                .withClaim("createdAt", new Date())
                .sign(algorithm);
    }

    public DecodedJWT decodeJWT(Algorithm algorithm, String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
