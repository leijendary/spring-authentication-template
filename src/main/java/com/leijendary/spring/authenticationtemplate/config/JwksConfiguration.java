package com.leijendary.spring.authenticationtemplate.config;

import com.leijendary.spring.authenticationtemplate.config.properties.AuthProperties;
import com.leijendary.spring.authenticationtemplate.security.JwtTools;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Configuration
@RequiredArgsConstructor
public class JwksConfiguration {

    private final AuthProperties authProperties;
    private final JwtTools jwtTools;

    @Bean
    public JWKSet jwkSet() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var key = new RSAKey.Builder(jwtTools.getAccessTokenPublicKey())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(authProperties.getKeyId())
                .build();

        return new JWKSet(key);
    }
}
