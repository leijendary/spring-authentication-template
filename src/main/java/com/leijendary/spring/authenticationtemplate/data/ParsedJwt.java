package com.leijendary.spring.authenticationtemplate.data;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ParsedJwt {

    private String issuer;
    private List<String> audience;
    private String subject;
    private OffsetDateTime issueTime;
    private OffsetDateTime expirationTime;
    private Set<String> scopes;
    private String id;
    private String accessTokenId;
    private boolean isVerified;
}
