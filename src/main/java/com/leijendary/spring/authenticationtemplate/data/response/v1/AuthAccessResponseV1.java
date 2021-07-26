package com.leijendary.spring.authenticationtemplate.data.response.v1;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class AuthAccessResponseV1 {

    private String token;
    private OffsetDateTime expiryDate;
}
