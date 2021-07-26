package com.leijendary.spring.authenticationtemplate.security;

import com.leijendary.spring.authenticationtemplate.config.properties.AuthProperties;
import com.leijendary.spring.authenticationtemplate.data.JwtParameters;
import com.leijendary.spring.authenticationtemplate.data.JwtSet;
import com.leijendary.spring.authenticationtemplate.data.ParsedJwt;
import com.leijendary.spring.authenticationtemplate.util.DateUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.UnsupportedCharsetException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static com.leijendary.spring.authenticationtemplate.security.JwtTools.TokenType.ACCESS_TOKEN;
import static com.leijendary.spring.authenticationtemplate.security.JwtTools.TokenType.REFRESH_TOKEN;
import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.now;
import static java.lang.String.join;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Base64.getDecoder;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTools {

    public static final String CLAIM_SCOPE = "scope";
    public static final String CLAIM_ATI = "ati";
    public static final String CLAIM_ISSUE_TIME = "iat";
    public static final String CLAIM_EXPIRY = "exp";

    private final AuthProperties authProperties;

    public enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    public JwtSet create(final JwtParameters parameters) {
        final var accessTokenExpiration = getExpiration(ACCESS_TOKEN);
        final var accessToken = create(parameters, accessTokenExpiration, ACCESS_TOKEN);
        final var refreshTokenExpiration = getExpiration(REFRESH_TOKEN);
        final var refreshToken = create(parameters, refreshTokenExpiration, REFRESH_TOKEN);

        return JwtSet.builder()
                .accessTokenId(parameters.getAccessTokenId())
                .accessToken(accessToken)
                .accessTokenExpiration(accessTokenExpiration)
                .refreshTokenId(parameters.getRefreshTokenId())
                .refreshToken(refreshToken)
                .refreshTokenExpiration(refreshTokenExpiration)
                .build();
    }

    public String create(final JwtParameters parameters, final OffsetDateTime expiration, final TokenType tokenType) {
        try {
            final var header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .type(JOSEObjectType.JWT)
                    .keyID(authProperties.getKeyId())
                    .build();
            var claims = new JWTClaimsSet.Builder()
                    .issuer(authProperties.getIssuer())
                    .audience(parameters.getAudience())
                    .subject(parameters.getSubject())
                    .claim(CLAIM_ISSUE_TIME, now().toEpochSecond())
                    .claim(CLAIM_EXPIRY, expiration.toEpochSecond())
                    .claim(CLAIM_SCOPE, join(" ", parameters.getScopes()));
            RSAPrivateKey privateKey;

            if (tokenType == ACCESS_TOKEN) {
                claims = claims.jwtID(parameters.getAccessTokenId().toString());
                privateKey = getAccessTokenPrivateKey();
            } else if (tokenType == REFRESH_TOKEN) {
                claims = claims
                        .jwtID(parameters.getRefreshTokenId().toString())
                        .claim(CLAIM_ATI, parameters.getAccessTokenId().toString());
                privateKey = getRefreshTokenPrivateKey();
            } else {
                throw new UnsupportedCharsetException("Unsupported tokenType: " + tokenType);
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

    public ParsedJwt parse(final String jwt) {
        try {
            final var signedJWT = SignedJWT.parse(jwt);
            final var claimsSet = signedJWT.getJWTClaimsSet();
            final var issueTime = ofNullable(claimsSet.getIssueTime())
                    .map(DateUtil::toOffsetDateTime)
                    .orElse(null);
            final var expirationTime = ofNullable(claimsSet.getExpirationTime())
                    .map(DateUtil::toOffsetDateTime)
                    .orElse(null);
            final var accessTokenId = claimsSet.getStringClaim(CLAIM_ATI);
            final var scopes = ofNullable(claimsSet.getStringClaim(CLAIM_SCOPE))
                    .map(s -> s.split(" "))
                    .map(Arrays::asList)
                    .map(HashSet::new)
                    .orElse(new HashSet<>());
            RSAPublicKey publicKey;

            if (ofNullable(accessTokenId).isEmpty()) {
                publicKey = getAccessTokenPublicKey();
            } else {
                publicKey = getRefreshTokenPublicKey();
            }

            final var verifier = new RSASSAVerifier(publicKey);
            final var isVerified = signedJWT.verify(verifier);

            return ParsedJwt.builder()
                    .issuer(claimsSet.getIssuer())
                    .audience(claimsSet.getAudience())
                    .subject(claimsSet.getSubject())
                    .issueTime(issueTime)
                    .expirationTime(expirationTime)
                    .scopes(scopes)
                    .id(claimsSet.getJWTID())
                    .accessTokenId(accessTokenId)
                    .isVerified(isVerified)
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | ParseException | JOSEException ignored) {
            return ParsedJwt.builder()
                    .isVerified(false)
                    .build();
        }
    }

    private OffsetDateTime getExpiration(final TokenType type) {
        var minutes = 0;

        if (type == ACCESS_TOKEN) {
            minutes = authProperties.getAccessToken().getExpiry();
        } else if (type == REFRESH_TOKEN) {
            minutes = authProperties.getRefreshToken().getExpiry();
        } else {
            throw new IllegalArgumentException("Token Type " + type.toString() + " is invalid.");
        }

        return now().plus(minutes, MINUTES);
    }

    private RSAPrivateKey getAccessTokenPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var privateKey = authProperties.getAccessToken().getPrivateKey()
                .replaceAll("\\n", "");

        return generatePrivateKey(privateKey);
    }

    private RSAPrivateKey getRefreshTokenPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var privateKey = authProperties.getRefreshToken().getPrivateKey()
                .replaceAll("\\n", "");

        return generatePrivateKey(privateKey);
    }

    private RSAPrivateKey generatePrivateKey(final String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var keySpec = new PKCS8EncodedKeySpec(getDecoder().decode(privateKey));
        final var keyFactory = getKeyFactory();

        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public RSAPublicKey getAccessTokenPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var publicKey = authProperties.getAccessToken().getPublicKey()
                .replaceAll("\\n", "");

        return generatePublicKey(publicKey);
    }

    private RSAPublicKey getRefreshTokenPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var publicKey = authProperties.getRefreshToken().getPublicKey()
                .replaceAll("\\n", "");

        return generatePublicKey(publicKey);
    }

    private RSAPublicKey generatePublicKey(final String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var keySpec = new X509EncodedKeySpec(getDecoder().decode(publicKey));
        final var keyFactory = getKeyFactory();

        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }
}
