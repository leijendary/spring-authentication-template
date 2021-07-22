package com.leijendary.spring.authenticationtemplate.controller.v1;

import com.leijendary.spring.authenticationtemplate.controller.AbstractController;
import com.nimbusds.jose.jwk.JWKSet;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.leijendary.spring.authenticationtemplate.controller.AbstractController.BASE_API_PATH;

@RestController
@RequestMapping(BASE_API_PATH + "/v1/.well-known/jwks.json")
@RequiredArgsConstructor
@Api("Endpoints for getting the public key for verifying the JWT")
public class JwksControllerV1 extends AbstractController {

    private final JWKSet jwkSet;

    @GetMapping
    public Map<String, Object> keys() {
        return jwkSet.toJSONObject();
    }
}
