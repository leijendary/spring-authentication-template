package com.leijendary.spring.authenticationtemplate.factory;

import com.leijendary.spring.authenticationtemplate.data.CredentialData;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;

public class CredentialDataFactory extends AbstractFactory {

    public static CredentialData of(final TokenRequestV1 tokenRequestV1) {
        return MAPPER.map(tokenRequestV1, CredentialData.class);
    }
}
