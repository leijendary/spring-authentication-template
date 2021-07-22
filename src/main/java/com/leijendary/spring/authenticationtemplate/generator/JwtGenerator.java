package com.leijendary.spring.authenticationtemplate.generator;

import com.leijendary.spring.authenticationtemplate.config.properties.AuthProperties;
import com.leijendary.spring.authenticationtemplate.data.Jwt;
import com.leijendary.spring.authenticationtemplate.data.JwtParameters;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Date;

import static com.leijendary.spring.authenticationtemplate.generator.JwtGenerator.TokenType.ACCESS_TOKEN;
import static com.leijendary.spring.authenticationtemplate.generator.JwtGenerator.TokenType.REFRESH_TOKEN;
import static java.lang.String.join;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Base64.getDecoder;
import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtGenerator {

    public static final String CLAIM_SCOPE = "scope";
    public static final String CLAIM_ATI = "ati";
    public static final String CLAIM_ISSUE_TIME = "iat";
    public static final String CLAIM_EXPIRY = "exp";

    private final AuthProperties authProperties;

    public enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    public Jwt jwt(final JwtParameters parameters) {
        final var accessTokenExpiration = getExpiration(ACCESS_TOKEN);
        final var accessToken = createToken(parameters, ACCESS_TOKEN, accessTokenExpiration);
        final var refreshTokenExpiration = getExpiration(REFRESH_TOKEN);
        final var refreshToken = createToken(parameters, REFRESH_TOKEN, refreshTokenExpiration);

        return Jwt.builder()
                .accessToken(accessToken)
                .accessTokenExpiration(accessTokenExpiration)
                .refreshToken(refreshToken)
                .refreshTokenExpiration(refreshTokenExpiration)
                .build();
    }

    public String createToken(final JwtParameters parameters, final TokenType tokenType, final Date expiration) {
        try {
            final var privateKey = getPrivateKey();
            final var header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .type(JOSEObjectType.JWT)
                    .keyID(authProperties.getKeyId())
                    .build();
            var claims = new JWTClaimsSet.Builder()
                    .issuer(authProperties.getIssuer())
                    .audience(parameters.getAudience())
                    .subject(parameters.getSubject())
                    .claim(CLAIM_ISSUE_TIME, new Date().getTime())
                    .claim(CLAIM_EXPIRY, expiration.getTime())
                    .claim(CLAIM_SCOPE, join(" ", parameters.getScopes()));

            if (tokenType == ACCESS_TOKEN) {
                claims = claims.jwtID(parameters.getTokenId());
            } else if (tokenType == REFRESH_TOKEN) {
                claims = claims
                        .jwtID(randomUUID().toString())
                        .claim(CLAIM_ATI, parameters.getTokenId());
            }

            final var signer = new RSASSASigner(privateKey);
            final var signed = new SignedJWT(header, claims.build());
            signed.sign(signer);

            return signed.serialize();
        } catch (Exception e) {
            log.error("JWT was not generated", e);

            throw new RuntimeException(e);
        }
    }

    public Date getExpiration(final TokenType type) {
        var minutes = 0;

        if (type == ACCESS_TOKEN) {
            minutes = authProperties.getAccessToken().getExpiry();
        } else if (type == REFRESH_TOKEN) {
            minutes = authProperties.getRefreshToken().getExpiry();
        } else {
            throw new IllegalArgumentException("Token Type " + type.toString() + " is invalid.");
        }

        final var now = Instant.now().plus(minutes, MINUTES);

        return Date.from(now);
    }

    private RSAPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var privateKey = authProperties.getPrivateKey().replaceAll("\\n", "");
        final var keySpec = new PKCS8EncodedKeySpec(getDecoder().decode(privateKey));
        final var keyFactory = getKeyFactory();

        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public RSAPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var publicKey = authProperties.getPublicKey().replaceAll("\\n", "");
        final var keySpec = new X509EncodedKeySpec(getDecoder().decode(publicKey));
        final var keyFactory = getKeyFactory();

        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }
}
