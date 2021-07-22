package com.leijendary.spring.authenticationtemplate.data.request.v1;

import lombok.Data;

@Data
public class TokenRefreshRequestV1 {

    private String refreshToken;
}
