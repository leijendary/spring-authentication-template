package com.leijendary.spring.authenticationtemplate.data.request.v1;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshRequestV1 {

    @NotBlank(message = "validation.required")
    private String refreshToken;
}
