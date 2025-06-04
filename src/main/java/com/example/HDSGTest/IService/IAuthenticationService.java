package com.example.HDSGTest.IService;

import com.example.HDSGTest.dto.request.AuthenticationRequest;
import com.example.HDSGTest.dto.request.IntrospectRequest;
import com.example.HDSGTest.dto.response.AuthenticationResponse;
import com.example.HDSGTest.dto.response.IntrospectResponse;
import com.example.HDSGTest.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;


public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    IntrospectResponse introspectJWT(IntrospectRequest request) throws JOSEException, ParseException;

    String generateToken(User user, int expirationDay);

    SignedJWT verifyToken(String token) throws JOSEException, ParseException;

    AuthenticationResponse refreshToken(String refreshToken);

    void logout(String authHeader);
}
