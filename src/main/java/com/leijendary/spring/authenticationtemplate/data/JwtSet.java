package com.leijendary.spring.authenticationtemplate.data;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class JwtSet {

    private UUID accessTokenId;
    private String accessToken;
    private Date accessTokenExpiration;
    private UUID refreshTokenId;
    private String refreshToken;
    private Date refreshTokenExpiration;
}
