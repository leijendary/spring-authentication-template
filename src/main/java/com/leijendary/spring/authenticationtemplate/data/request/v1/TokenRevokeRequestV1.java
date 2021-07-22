package com.leijendary.spring.authenticationtemplate.data.request.v1;

import lombok.Data;

@Data
public class TokenRevokeRequestV1 {

    private String accessToken;
}
