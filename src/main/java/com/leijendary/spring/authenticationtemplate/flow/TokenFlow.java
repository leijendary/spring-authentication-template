package com.leijendary.spring.authenticationtemplate.flow;

import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.factory.AuthDataFactory;
import com.leijendary.spring.authenticationtemplate.factory.CredentialDataFactory;
import com.leijendary.spring.authenticationtemplate.service.AuthService;
import com.leijendary.spring.authenticationtemplate.service.IamAccountService;
import com.leijendary.spring.authenticationtemplate.service.IamUserCredentialService;
import com.leijendary.spring.authenticationtemplate.service.IamUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.leijendary.spring.authenticationtemplate.factory.AuthFactory.toTokenResponseV1;

@Component
@RequiredArgsConstructor
public class TokenFlow {

    private final AuthService authService;
    private final IamAccountService iamAccountService;
    private final IamUserCredentialService iamUserCredentialService;
    private final IamUserService iamUserService;

    @Transactional
    public TokenResponseV1 createV1(final TokenRequestV1 tokenRequest) {
        final var credentialData = CredentialDataFactory.of(tokenRequest);
        // First validate the username and password of the user
        final var iamCredential = iamUserCredentialService.matchPassword(credentialData);
        final var iamUser = iamCredential.getUser();
        final var iamAccount = iamUser.getAccount();

        // Check if the user's status is still in a valid state
        iamUserService.checkUserStatus(iamUser);

        // Check if the account's status is still in a valid state
        iamAccountService.checkAccountStatus(iamAccount);

        // Create the Auth object
        final var authData = AuthDataFactory.of(tokenRequest);
        final var auth = authService.create(authData);

        // Get the scopes of the uer
        final var scopes = iamUserService.getScopes()

        return toTokenResponseV1(auth);
    }

    @Transactional
    public TokenResponseV1 refreshV1(final TokenRefreshRequestV1 refreshRequest) {
        final var auth = tokenService.refresh(refreshRequest);

        return toTokenResponseV1(auth);
    }

    public void revokeV1(final TokenRevokeRequestV1 revokeRequest) {
        tokenService.revoke(revokeRequest);
    }
}
