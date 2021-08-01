package com.leijendary.spring.authenticationtemplate.flow;

import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.leijendary.spring.authenticationtemplate.factory.AuthFactory.toTokenResponseV1;

@Component
@RequiredArgsConstructor
public class TokenFlow {

    private final TokenService tokenService;

    @Transactional
    public TokenResponseV1 createV1(final TokenRequestV1 tokenRequest) {
        final var auth = tokenService.create(tokenRequest);

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
