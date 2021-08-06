package com.leijendary.spring.authenticationtemplate.factory;

import com.leijendary.schema.AuthSchema;
import com.leijendary.spring.authenticationtemplate.data.AuthData;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.model.Auth;

public class AuthFactory extends AbstractFactory {

    public static AuthSchema toSchema(final Auth auth) {
        return MAPPER.map(auth, AuthSchema.class);
    }

    public static TokenResponseV1 toTokenResponseV1(final Auth auth) {
        return MAPPER.map(auth, TokenResponseV1.class);
    }

    public static Auth of(final AuthData authData) {
        return MAPPER.map(authData, Auth.class);
    }
}
