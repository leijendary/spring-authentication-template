package com.leijendary.spring.authenticationtemplate.data.response.v1;

import lombok.Data;

import java.time.Instant;

@Data
public class AuthRefreshResponseV1 {

    private String token;
    private Instant expiryDate;
}
