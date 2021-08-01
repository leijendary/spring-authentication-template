package com.leijendary.spring.authenticationtemplate.flow;

import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.factory.AuthDataFactory;
import com.leijendary.spring.authenticationtemplate.factory.CredentialDataFactory;
import com.leijendary.spring.authenticationtemplate.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.leijendary.spring.authenticationtemplate.data.TokenType.ACCESS_TOKEN;
import static com.leijendary.spring.authenticationtemplate.data.TokenType.REFRESH_TOKEN;
import static com.leijendary.spring.authenticationtemplate.factory.AuthFactory.toTokenResponseV1;

@Component
@RequiredArgsConstructor
public class TokenFlow {

    private final AuthService authService;
    private final IamAccountService iamAccountService;
    private final IamRoleService iamRoleService;
    private final IamUserCredentialService iamUserCredentialService;
    private final IamUserService iamUserService;
    private final TokenService tokenService;

    @Transactional
    public TokenResponseV1 createV1(final TokenRequestV1 tokenRequestV1) {
        final var credentialData = CredentialDataFactory.of(tokenRequestV1);
        // First validate the username and password of the user
        final var iamCredential = iamUserCredentialService.matchPassword(credentialData);
        final var iamUser = iamCredential.getUser();
        final var iamAccount = iamUser.getAccount();

        // Check if the user's status is still in a valid state
        iamUserService.checkUserStatus(iamUser);

        // Check if the account's status is still in a valid state
        iamAccountService.checkAccountStatus(iamAccount);

        final var userId = iamUser.getId();
        final var authData = AuthDataFactory.of(tokenRequestV1);
        authData.setUserId(userId);

        // Create the Auth object
        final var auth = authService.create(authData);

        // Get the scopes of the role of the user
        final var iamRole = iamUser.getRole();
        final var scopes = iamRoleService.getScopes(iamRole);

        // Generate access and refresh tokens. Set then to the Auth object
        authService.generateAndSetTokens(auth, scopes);

        return toTokenResponseV1(auth);
    }

    @Transactional
    public TokenResponseV1 refreshV1(final TokenRefreshRequestV1 refreshRequest) {
        final var refreshToken = refreshRequest.getRefreshToken();
        final var parsedJwt = tokenService.parseJwt(refreshToken, REFRESH_TOKEN);
        final var auth = authService.refreshToken(parsedJwt);
        final var userId = auth.getUserId();
        final var iamUser = iamUserService.get(userId);
        final var iamAccount = iamUser.getAccount();

        // Check if the user's status is still in a valid state
        iamUserService.checkUserStatus(iamUser);

        // Check if the account's status is still in a valid state
        iamAccountService.checkAccountStatus(iamAccount);

        // Get the scopes of the role of the user
        final var iamRole = iamUser.getRole();
        final var scopes = iamRoleService.getScopes(iamRole);

        // Generate access and refresh tokens. Set then to the Auth object
        authService.generateAndSetTokens(auth, scopes);

        return toTokenResponseV1(auth);
    }

    public void revokeV1(final TokenRevokeRequestV1 revokeRequest) {
        final var accessToken = revokeRequest.getAccessToken();
        final var parsedJwt = tokenService.parseJwt(accessToken, ACCESS_TOKEN);

        authService.removeByAccessId(parsedJwt.getId());
    }
}
