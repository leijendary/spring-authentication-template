package com.leijendary.spring.authenticationtemplate.data;

import lombok.Data;

@Data
public class CredentialData {

    private String username;
    private String password;
    private String type;
}
