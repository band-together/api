package com.band.api.services;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.band.api.domain.User;
import com.band.api.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final String TOKEN_SECRET;
    private JWTUtil jwtUtil;

    @Autowired
    public JWTService(@Value("${JWTSecret}") String jwtSecret, JWTUtil jwtUtil) {
        TOKEN_SECRET = jwtSecret;
        this.jwtUtil = jwtUtil;
    }

    public String createToken(User user) {
        try {
            Algorithm algorithm = jwtUtil.getAlgorithm(TOKEN_SECRET);
            String token = jwtUtil.create(user.getId(), algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            //log Token Signing Failed
        }
        return null;
    }

    public Integer getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = jwtUtil.getAlgorithm(TOKEN_SECRET);
            DecodedJWT jwt = jwtUtil.decodeJWT(algorithm, token);
            return jwt.getClaim("id").asInt();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            //log Token Verification Failed
            return null;
        }
    }
}