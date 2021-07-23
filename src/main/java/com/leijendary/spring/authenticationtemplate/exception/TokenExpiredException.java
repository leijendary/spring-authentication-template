package com.leijendary.spring.authenticationtemplate.exception;

import lombok.Getter;

public class TokenExpiredException extends RuntimeException {

    @Getter
    private final String source;

    public TokenExpiredException(final String source) {
        super();

        this.source = source;
    }
}
