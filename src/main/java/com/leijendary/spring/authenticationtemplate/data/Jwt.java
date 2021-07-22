package com.leijendary.spring.authenticationtemplate.data;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Jwt {

    private String accessTokenId;
    private String accessToken;
    private Date accessTokenExpiration;
    private String refreshTokenId;
    private String refreshToken;
    private Date refreshTokenExpiration;
}
