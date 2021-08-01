package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.ParsedJwt;
import com.leijendary.spring.authenticationtemplate.exception.InvalidTokenSignatureException;
import com.leijendary.spring.authenticationtemplate.security.JwtTools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService extends AbstractService {

    private final JwtTools jwtTools;

    public ParsedJwt parseJwt(final String token, final String tokenType) {
        final var parsedJwt = jwtTools.parse(token);

        if (!parsedJwt.isVerified()) {
            throw new InvalidTokenSignatureException(tokenType);
        }

        return parsedJwt;
    }
}
