package com.leijendary.spring.authenticationtemplate.data.request.v1;

import lombok.Data;

@Data
public class TokenRequestV1 {

    private String username;
    private String password;
    private String audience;
    private String type;
    private String deviceId;
}
