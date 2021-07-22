package com.leijendary.spring.authenticationtemplate.data.response.v1;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenResponseV1 implements Serializable {

    private AuthAccessResponseV1 access;
    private AuthRefreshResponseV1 refresh;
}
