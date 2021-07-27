package com.leijendary.spring.authenticationtemplate.data.request.v1;

import com.leijendary.spring.authenticationtemplate.validator.annotation.v1.AuthTypeV1;
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
    @AuthTypeV1
    private String type;

    @NotBlank(message = "validation.required")
    private String deviceId;
}
