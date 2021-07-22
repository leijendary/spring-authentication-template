package com.leijendary.spring.authenticationtemplate.data;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtParameters {

    // Unique identifier of the token. Will be assigned to "jti"
    private String tokenId;
    // Identifier of the user to give the access to. Value should be from "userId"
    private String subject;
    // The identifier of the requesting party. Normally the URL of the client
    private String audience;
    private Set<String> scopes;
}
