package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.JwtParameters;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.exception.*;
import com.leijendary.spring.authenticationtemplate.factory.AuthFactory;
import com.leijendary.spring.authenticationtemplate.model.*;
import com.leijendary.spring.authenticationtemplate.repository.*;
import com.leijendary.spring.authenticationtemplate.security.JwtTools;
import com.leijendary.spring.authenticationtemplate.specification.NonDeactivatedAccountUser;
import com.leijendary.spring.authenticationtemplate.specification.NonDeactivatedAccountUserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.leijendary.spring.authenticationtemplate.data.Status.ACTIVE;
import static com.leijendary.spring.authenticationtemplate.data.Status.INCOMPLETE;
import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.now;
import static java.util.Arrays.asList;
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
    public TokenResponseV1 create(final TokenRequestV1 tokenRequest) {
        final var specification = NonDeactivatedAccountUserCredential.builder()
                .username(tokenRequest.getUsername())
                .type(tokenRequest.getType())
                .build();
        final var credential = iamUserCredentialRepository.findOne(specification)
                .orElseThrow(() -> new InvalidCredentialException(tokenRequest.getUsername(), "username"));

        // Validate password
        if (!passwordEncoder.matches(tokenRequest.getPassword(), credential.getPassword())) {
            throw new InvalidCredentialException(tokenRequest.getUsername(), "username");
        }

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
                .createdDate(now())
                .build();

        authRepository.save(auth);

        final var scopes = getScopes(user);

        generateAndSetTokens(auth, scopes);

        credential.setLastUsedDate(now());

        iamUserCredentialRepository.save(credential);

        return AuthFactory.toTokenResponseV1(auth);
    }

    @Transactional
    public TokenResponseV1 refresh(final TokenRefreshRequestV1 refreshRequest) {
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

        final var specification = NonDeactivatedAccountUser.builder().build();
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

        return AuthFactory.toTokenResponseV1(auth);
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

    private Set<String> getScopes(final IamUser user) {
        final var permissions = ofNullable(user.getRole())
                .map(IamRole::getPermissions)
                .orElse(new HashSet<>());

        return formatToScope(permissions);
    }

    private Set<String> formatToScope(final Set<IamPermission> permissions) {
        return permissions
                .stream()
                .map(IamPermission::getPermission)
                .collect(Collectors.toSet());
    }

    private void checkUserStatus(final IamUser user) {
        final var statuses = asList(ACTIVE, INCOMPLETE);

        if (!statuses.contains(user.getStatus())) {
            throw new NotActiveException("user.status", "access.user.inactive");
        }
    }

    private void checkAccountStatus(final IamAccount account) {
        if (account != null && !account.getStatus().equals(ACTIVE)) {
            throw new NotActiveException("account.status", "access.account.inactive");
        }
    }
}
