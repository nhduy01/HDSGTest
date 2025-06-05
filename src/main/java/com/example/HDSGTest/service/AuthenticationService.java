package com.example.HDSGTest.service;

import com.example.HDSGTest.Exception.AppException;
import com.example.HDSGTest.Exception.ErrorCode;
import com.example.HDSGTest.IService.IAuthenticationService;
import com.example.HDSGTest.dto.response.AuthenticationResponse;
import com.example.HDSGTest.dto.request.IntrospectRequest;
import com.example.HDSGTest.dto.request.AuthenticationRequest;
import com.example.HDSGTest.dto.response.IntrospectResponse;
import com.example.HDSGTest.dto.request.RefreshTokenRequest;
import com.example.HDSGTest.entity.User;
import com.example.HDSGTest.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String jwtSecret;

    public IntrospectResponse introspectJWT(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean valid = false;

        try {
            verifyToken(token);
            valid = true;
        } catch (RuntimeException e) {
            valid = false;
        }

        return IntrospectResponse
                .builder()
                .valid(valid)
                .build();
    }

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiredDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!(verified && expiredDate.after(new Date()))) {
            throw new RuntimeException("String.valueOf(ErrorCode.UNAUTHENTICATED)");
        }

        return signedJWT;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Đăng nhập thất bại cho tên đăng nhập: {}", request.getUsername());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user, 1);
        var refreshToken = generateToken(user, 365);

        // Lưu refreshToken vào DB
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        log.info("Người dùng {} đã đăng nhập", user.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        try {
            verifyToken(refreshToken);
        } catch (Exception e) {
            log.warn("Làm mới token thất bại cho người dùng: {}", user.getUsername());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String newAccessToken = generateToken(user, 1);
        log.info("Người dùng {} đã làm mới token", user.getUsername());
        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }

    public String generateToken(User user, int expirationDay) {
        Date now = new Date();
        Instant nowInstant = now.toInstant();
        Instant expirationInstant = nowInstant.plus(expirationDay, ChronoUnit.DAYS);
        Date expirationTime = Date.from(expirationInstant);

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("NHD")
                .issueTime(now)
                .expirationTime(expirationTime)
                .jwtID(UUID.randomUUID().toString())
                .claim("userId", user.getId())
                .claim("scope", buildScope(user))
                .claim("username", user.getUsername())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(jwtSecret.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot Create JWT", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(User user) {
        return "user";
    }

    public void logout() {
        // Lấy user hiện tại từ SecurityContextHolder
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        user.setRefreshToken(null);
        userRepository.save(user);
        log.info("Người dùng {} đã đăng xuất", username);
    }
}
