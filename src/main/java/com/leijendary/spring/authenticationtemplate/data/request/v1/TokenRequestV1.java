package com.leijendary.spring.authenticationtemplate.data.request.v1;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRequestV1 {

    @NotBlank(message = "validation.required")
    private String username;

    @NotBlank(message = "validation.required")
    private String password;

    @NotBlank(message = "validation.required")
    private String audience;

    @NotBlank(message = "validation.required")
    private String type;

    @NotBlank(message = "validation.required")
    private String deviceId;
}
