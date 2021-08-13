package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.AuthData;
import com.leijendary.spring.authenticationtemplate.data.JwtParameters;
import com.leijendary.spring.authenticationtemplate.data.ParsedJwt;
import com.leijendary.spring.authenticationtemplate.exception.InvalidTokenException;
import com.leijendary.spring.authenticationtemplate.exception.TokenExpiredException;
import com.leijendary.spring.authenticationtemplate.factory.AuthFactory;
import com.leijendary.spring.authenticationtemplate.model.Auth;
import com.leijendary.spring.authenticationtemplate.model.AuthAccess;
import com.leijendary.spring.authenticationtemplate.model.AuthRefresh;
import com.leijendary.spring.authenticationtemplate.repository.AuthAccessRepository;
import com.leijendary.spring.authenticationtemplate.repository.AuthRefreshRepository;
import com.leijendary.spring.authenticationtemplate.repository.AuthRepository;
import com.leijendary.spring.authenticationtemplate.security.JwtTools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.leijendary.spring.authenticationtemplate.util.RequestContext.now;
import static java.util.Optional.ofNullable;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class AuthService extends AbstractService {

    private final AuthAccessRepository authAccessRepository;
    private final AuthRefreshRepository authRefreshRepository;
    private final AuthRepository authRepository;
    private final JwtTools jwtTools;

    public Auth create(final AuthData authData) {
        final var auth = AuthFactory.of(authData);
        auth.setCreatedDate(now());

        return authRepository.save(auth);
    }

    @Transactional
    public Auth refreshToken(final ParsedJwt parsedJwt) {
        final var auth = authRepository.findFirstByRefreshId(fromString(parsedJwt.getId()))
                .orElseThrow(() -> new InvalidTokenException("refreshToken"));
        final var isExpired = ofNullable(parsedJwt.getExpirationTime())
                .map(expirationTime -> expirationTime.isBefore(now()))
                .orElse(true);

        if (isExpired) {
            throw new TokenExpiredException("refreshToken");
        }

        // Delete old tokens
        authAccessRepository.delete(auth.getAccess());
        authRefreshRepository.delete(auth.getRefresh());

        // Set access and refresh tokens to null since they should be deleted
        auth.setAccess(null);
        auth.setRefresh(null);

        return auth;
    }

    public void removeByAccessId(final String id) {
        authRepository
                .findFirstByAccessId(fromString(id))
                .ifPresent(authRepository::delete);
    }

    @Transactional
    public void generateAndSetTokens(final Auth auth, final Set<String> scopes) {
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
