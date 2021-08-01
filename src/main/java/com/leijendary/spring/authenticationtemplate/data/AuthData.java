package com.leijendary.spring.authenticationtemplate.data;

import lombok.Data;

@Data
public class AuthData {

    private long userId;
    private String username;
    private String audience;
    private String type;
    private String deviceId;
}
