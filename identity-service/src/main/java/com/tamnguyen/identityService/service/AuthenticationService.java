package com.tamnguyen.identityService.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tamnguyen.identityService.dto.request.AuthenticationRequest;
import com.tamnguyen.identityService.dto.request.IntrospectRequest;
import com.tamnguyen.identityService.dto.request.LogoutRequest;
import com.tamnguyen.identityService.dto.request.RefreshRequest;
import com.tamnguyen.identityService.dto.response.AuthenticationResponse;
import com.tamnguyen.identityService.dto.response.IntrospectResponse;
import com.tamnguyen.identityService.dto.response.TokenInfo;
import com.tamnguyen.identityService.entity.InvalidatedToken;
import com.tamnguyen.identityService.entity.User;
import com.tamnguyen.identityService.exception.AppException;
import com.tamnguyen.identityService.exception.ErrorCode;
import com.tamnguyen.identityService.mapper.UserMapper;
import com.tamnguyen.identityService.repository.InvalidatedTokenRepository;
import com.tamnguyen.identityService.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    private final UserMapper userMapper;

    @NonFinal
    @Value("${jwt.code.signerKey}")
    protected String codeSignerKey;

    @NonFinal
    @Value("${jwt.accessToken.signerKey}")
    protected String accessTokenSignerKey;

    @NonFinal
    @Value("${jwt.refreshToken.signerKey}")
    protected String refreshTokenSignerKey;

    @NonFinal
    @Value("${jwt.accessToken.expireTime}")
    protected long tokenExpiration;

    @NonFinal
    @Value("${jwt.refreshToken.expireTime}")
    protected long refreshTokenExpiration;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var accessToken = generateToken(user, accessTokenSignerKey, tokenExpiration);
        var refreshToken = generateToken(user, refreshTokenSignerKey, refreshTokenExpiration);

        return AuthenticationResponse.builder()
                .user(userMapper.toUserResponse(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var accessToken = verifyToken(request.getAccessToken(), accessTokenSignerKey);
        var refreshToken = verifyToken(request.getRefreshToken(), refreshTokenSignerKey);

        addInvalidatedToken(accessToken);
        addInvalidatedToken(refreshToken);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getRefreshToken(), refreshTokenSignerKey);

        var jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jwtId).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var accessToken = generateToken(user, accessTokenSignerKey, tokenExpiration);
        var refreshToken = generateToken(user, refreshTokenSignerKey, refreshTokenExpiration);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenInfo generateToken(User user, String signerKey, long tokenExpiration) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        Date issueTime = new Date();
        Date expiryTime = new Date(Instant.ofEpochMilli(issueTime.getTime())
                .plus(tokenExpiration, ChronoUnit.MILLIS)
                .toEpochMilli());

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("tamnguyen.com")
                .issueTime(issueTime)
                .expirationTime(expiryTime)
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("userId", user.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            JWSSigner signer = new MACSigner(signerKey);
            jwsObject.sign(signer);

            return new TokenInfo(jwsObject.serialize(), expiryTime);
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, accessTokenSignerKey);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    public SignedJWT verifyToken (String token, String signerKey) throws  JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private void addInvalidatedToken(SignedJWT signedJWT) throws ParseException {
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jwtId).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}
