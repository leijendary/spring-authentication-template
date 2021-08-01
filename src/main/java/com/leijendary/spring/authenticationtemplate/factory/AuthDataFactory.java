package com.leijendary.spring.authenticationtemplate.factory;

import com.leijendary.spring.authenticationtemplate.data.AuthData;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;

public class AuthDataFactory extends AbstractFactory {

    public static AuthData of(final TokenRequestV1 tokenRequestV1) {
        return MAPPER.map(tokenRequestV1, AuthData.class);
    }
}
