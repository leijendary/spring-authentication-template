package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.JwtParameters;
import com.leijendary.spring.authenticationtemplate.data.Status;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.exception.InvalidCredentialException;
import com.leijendary.spring.authenticationtemplate.exception.NotActiveException;
import com.leijendary.spring.authenticationtemplate.exception.ResourceNotFoundException;
import com.leijendary.spring.authenticationtemplate.factory.AuthFactory;
import com.leijendary.spring.authenticationtemplate.generator.JwtGenerator;
import com.leijendary.spring.authenticationtemplate.model.*;
import com.leijendary.spring.authenticationtemplate.repository.*;
import com.leijendary.spring.authenticationtemplate.validator.v1.TokenRefreshRequestV1Validator;
import com.leijendary.spring.authenticationtemplate.validator.v1.TokenRequestV1Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService extends AbstractService {

    private final AuthAccessRepository authAccessRepository;
    private final AuthRefreshRepository  authRefreshRepository;
    private final AuthRepository authRepository;
    private final IamUserCredentialRepository iamUserCredentialRepository;
    private final IamUserRepository iamUserRepository;
    private final JwtGenerator jwtGenerator;
    private final TokenRefreshRequestV1Validator tokenRefreshRequestV1Validator;
    private final TokenRequestV1Validator tokenRequestV1Validator;

    @Transactional
    public TokenResponseV1 create(final TokenRequestV1 tokenRequest) {
        validate(tokenRequestV1Validator, tokenRequest, TokenRequestV1.class);

        final var credential = iamUserCredentialRepository
                .findFirstByUsernameIgnoreCase(tokenRequest.getUsername())
                .orElseThrow(() -> new InvalidCredentialException(tokenRequest.getUsername(), "username"));
        final var user = credential.getUser();
        final var account = user.getAccount();

        checkUserStatus(user);
        checkAccountStatus(account);

        final var auth = Auth.builder()
                .userId(user.getId())
                .username(tokenRequest.getUsername())
                .audience(tokenRequest.getAudience())
                .type(tokenRequest.getType())
                .deviceId(tokenRequest.getDeviceId())
                .createdDate(Instant.now())
                .build();

        authRepository.save(auth);

        final var scopes = formatScopes(user.getRole().getPermissions());

        generateAndSaveTokens(auth, credential.getUsername(), tokenRequest.getAudience(), scopes);

        credential.setLastUsedDate(Instant.now());

        iamUserCredentialRepository.save(credential);

        return AuthFactory.toTokenResponseV1(auth);
    }

    @Transactional
    public TokenResponseV1 refresh(final TokenRefreshRequestV1 refreshRequest) {
        validate(tokenRefreshRequestV1Validator, refreshRequest, TokenRefreshRequestV1.class);

        final var auth = authRepository.findFirstByRefreshToken(refreshRequest.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException("refreshToken", refreshRequest.getRefreshToken()));
        final var user = iamUserRepository.findById(auth.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", auth.getUserId()));
        final var account = user.getAccount();

        checkUserStatus(user);
        checkAccountStatus(account);

        final var scopes = formatScopes(user.getRole().getPermissions());

        generateAndSaveTokens(auth, auth.getUsername(), auth.getAudience(), scopes);

        return AuthFactory.toTokenResponseV1(auth);
    }

    public void revoke(final TokenRevokeRequestV1 revokeRequest) {
        authRepository
                .findFirstByAccessToken(revokeRequest.getAccessToken())
                .ifPresent((auth) -> {
                    authAccessRepository.delete(auth.getAccess());
                    authRefreshRepository.delete(auth.getRefresh());
                    authRepository.delete(auth);
                });
    }

    private void generateAndSaveTokens(final Auth auth, final String username, final String audience,
                                       final Set<String> scopes) {
        final var jwtParameters = JwtParameters.builder()
                .tokenId(String.valueOf(auth.getId()))
                .subject(username)
                .audience(audience)
                .scopes(scopes)
                .build();
        final var jwt = jwtGenerator.jwt(jwtParameters);
        final var authAccess = AuthAccess.builder()
                .auth(auth)
                .token(jwt.getAccessToken())
                .expiryDate(jwt.getAccessTokenExpiration().toInstant())
                .build();
        final var authRefresh = AuthRefresh.builder()
                .auth(auth)
                .token(jwt.getRefreshToken())
                .expiryDate(jwt.getRefreshTokenExpiration().toInstant())
                .build();

        Optional.ofNullable(auth.getAccess())
                .ifPresent((access) -> authAccess.setId(access.getId()));

        auth.setAccess(authAccess);

        authAccessRepository.save(authAccess);

        Optional.ofNullable(auth.getRefresh())
                .ifPresent((refresh) -> authRefresh.setId(refresh.getId()));

        auth.setRefresh(authRefresh);

        authRefreshRepository.save(authRefresh);

        authRepository.save(auth);
    }

    private Set<String> formatScopes(final Set<IamPermission> permissions) {
        return permissions
                .stream()
                .map(IamPermission::getPermission)
                .collect(Collectors.toSet());
    }

    private void checkUserStatus(final IamUser user) {
        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new NotActiveException("user.status", "access.user.inactive");
        }
    }

    private void checkAccountStatus(final IamAccount account) {
        if (!account.getStatus().equals(Status.ACTIVE)) {
            throw new NotActiveException("account.status", "access.account.inactive");
        }
    }
}
