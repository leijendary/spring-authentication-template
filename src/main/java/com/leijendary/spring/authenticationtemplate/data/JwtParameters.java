package com.leijendary.spring.authenticationtemplate.data;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class JwtParameters {

    // Unique identifier of the access token. Will be assigned to "jti" and "ati" of the refresh token
    private UUID accessTokenId;
    // Unique identifier of the refresh token. Will be assigned to "jti"
    private UUID refreshTokenId;
    // Identifier of the user to give the access to. Value should be from "userId"
    private String subject;
    // The identifier of the requesting party. Normally the URL of the client
    private String audience;
    private Set<String> scopes;
}
