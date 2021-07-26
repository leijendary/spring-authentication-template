package com.leijendary.spring.authenticationtemplate.data;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class JwtSet {

    private UUID accessTokenId;
    private String accessToken;
    private OffsetDateTime accessTokenExpiration;
    private UUID refreshTokenId;
    private String refreshToken;
    private OffsetDateTime refreshTokenExpiration;
}
