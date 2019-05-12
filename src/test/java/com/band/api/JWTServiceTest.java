package com.band.api;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.band.api.domain.User;
import com.band.api.services.JWTService;
import com.band.api.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JWTServiceTest {

    JWTUtil jwtUtil;
    JWTService jwtService;
    Algorithm mockAlgorithm;

    @BeforeEach
    void setup() {
        jwtUtil = Mockito.mock(JWTUtil.class);
        jwtService = new JWTService("MySecret!", jwtUtil);
        mockAlgorithm = Mockito.mock(Algorithm.class);
    }

    @Test
    void createTokenSuccess() {
        when(jwtUtil.getAlgorithm(anyString())).thenReturn(mockAlgorithm);
        when(jwtUtil.create(1, mockAlgorithm)).thenReturn("MyToken");
        assertEquals("MyToken", jwtService.createToken(User.builder().id(1).build()));
    }

    @Test
    void createTokenFail() {
        when(jwtUtil.getAlgorithm(anyString())).thenReturn(mockAlgorithm);
        when(jwtUtil.create(1, mockAlgorithm)).thenThrow(JWTCreationException.class);
        assertEquals(null, jwtService.createToken(User.builder().id(1).build()));
    }

    @Test
    void getUserIdFromTokenSuccess() {
        DecodedJWT mockDecodedJWT = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        String token = "MyToken";

        when(jwtUtil.getAlgorithm(anyString())).thenReturn(mockAlgorithm);
        when(jwtUtil.decodeJWT(mockAlgorithm, token)).thenReturn(mockDecodedJWT);
        when(mockDecodedJWT.getClaim("id")).thenReturn(mockClaim);
        when(mockClaim.asInt()).thenReturn(1);

        assertEquals(jwtService.getUserIdFromToken(token),1);
    }

    @Test
    void getUserIdFromTokenFail() {
        String token = "MyToken";
        when(jwtUtil.getAlgorithm(anyString())).thenReturn(mockAlgorithm);
        when(jwtUtil.decodeJWT(mockAlgorithm, token)).thenThrow(JWTVerificationException.class);

        assertEquals(jwtService.getUserIdFromToken(token),null);
    }
}
