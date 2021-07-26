package com.leijendary.spring.authenticationtemplate.data.response.v1;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class AuthRefreshResponseV1 {

    private String token;
    private OffsetDateTime expiryDate;
}
