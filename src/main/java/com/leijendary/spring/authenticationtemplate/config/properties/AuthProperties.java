package com.leijendary.spring.authenticationtemplate.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
@Data
public class AuthProperties {

    private String issuer;
    private String keyId;
    private AccessToken accessToken;
    private RefreshToken refreshToken;
    private String privateKey;
    private String publicKey;

    @Data
    public static class AccessToken {

        private int expiry;
    }

    @Data
    public static class RefreshToken {

        private int expiry;
    }
}
