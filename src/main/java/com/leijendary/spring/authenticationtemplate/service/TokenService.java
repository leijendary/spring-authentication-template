package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.JwtParameters;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.exception.InvalidTokenException;
import com.leijendary.spring.authenticationtemplate.exception.InvalidTokenSignatureException;
import com.leijendary.spring.authenticationtemplate.exception.ResourceNotFoundException;
import com.leijendary.spring.authenticationtemplate.exception.TokenExpiredException;
import com.leijendary.spring.authenticationtemplate.model.Auth;
import com.leijendary.spring.authenticationtemplate.model.AuthAccess;
import com.leijendary.spring.authenticationtemplate.model.AuthRefresh;
import com.leijendary.spring.authenticationtemplate.repository.*;
import com.leijendary.spring.authenticationtemplate.security.JwtTools;
import com.leijendary.spring.authenticationtemplate.specification.NonDeactivatedAccountUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.now;
import static java.util.Optional.ofNullable;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class TokenService extends AbstractService {

    private final AuthAccessRepository authAccessRepository;
    private final AuthRefreshRepository  authRefreshRepository;
    private final AuthRepository authRepository;
    private final IamUserCredentialRepository iamUserCredentialRepository;
    private final IamUserRepository iamUserRepository;
    private final JwtTools jwtTools;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Auth create(final TokenRequestV1 tokenRequest) {
        final var scopes = getScopes(user);

        generateAndSetTokens(auth, scopes);

        credential.setLastUsedDate(now());

        iamUserCredentialRepository.save(credential);

        return auth;
    }

    @Transactional
    public Auth refresh(final TokenRefreshRequestV1 refreshRequest) {
        final var parsedJwt = jwtTools.parse(refreshRequest.getRefreshToken());

        if (!parsedJwt.isVerified()) {
            throw new InvalidTokenSignatureException("refreshToken");
        }

        final var auth = authRepository.findFirstByRefreshId(fromString(parsedJwt.getId()))
                .orElseThrow(() -> new InvalidTokenException("refreshToken"));
        final var isExpired = ofNullable(parsedJwt.getExpirationTime())
                .map(expirationTime -> expirationTime.isBefore(now()))
                .orElse(true);

        if (isExpired) {
            throw new TokenExpiredException("refreshToken");
        }

        final var specification = NonDeactivatedAccountUser.builder()
                .userId(auth.getUserId())
                .build();
        final var user = iamUserRepository.findOne(specification)
                .orElseThrow(() -> new ResourceNotFoundException("user", auth.getUserId()));
        final var account = user.getAccount();

        checkUserStatus(user);
        checkAccountStatus(account);

        final var scopes = getScopes(user);

        // Delete old tokens
        authAccessRepository.delete(auth.getAccess());
        authRefreshRepository.delete(auth.getRefresh());

        generateAndSetTokens(auth, scopes);

        return auth;
    }

    public void revoke(final TokenRevokeRequestV1 revokeRequest) {
        final var parsedJwt = jwtTools.parse(revokeRequest.getAccessToken());

        if (!parsedJwt.isVerified()) {
            throw new InvalidTokenSignatureException("accessToken");
        }

        authRepository
                .findFirstByAccessId(fromString(parsedJwt.getId()))
                .ifPresent(authRepository::delete);
    }

    private void generateAndSetTokens(final Auth auth, final Set<String> scopes) {
        final var jwtParameters = JwtParameters.builder()
                .accessTokenId(randomUUID())
                .refreshTokenId(randomUUID())
                .subject(String.valueOf(auth.getUserId()))
                .audience(auth.getAudience())
                .scopes(scopes)
                .build();
        final var jwt = jwtTools.create(jwtParameters);
        final var authAccess = AuthAccess.builder()
                .id(jwt.getAccessTokenId())
                .auth(auth)
                .token(jwt.getAccessToken())
                .expiryDate(jwt.getAccessTokenExpiration())
                .build();

        auth.setAccess(authAccess);

        final var authRefresh = AuthRefresh.builder()
                .id(jwt.getRefreshTokenId())
                .auth(auth)
                .accessTokenId(jwt.getAccessTokenId())
                .token(jwt.getRefreshToken())
                .expiryDate(jwt.getRefreshTokenExpiration())
                .build();

        auth.setRefresh(authRefresh);
    }
}
