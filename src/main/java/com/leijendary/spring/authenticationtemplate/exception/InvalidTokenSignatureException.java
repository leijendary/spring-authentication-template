package com.leijendary.spring.authenticationtemplate.exception;

import lombok.Getter;

public class InvalidTokenSignatureException extends RuntimeException {

    @Getter
    private final String source;

    public InvalidTokenSignatureException(final String source) {
        super();

        this.source = source;
    }
}
