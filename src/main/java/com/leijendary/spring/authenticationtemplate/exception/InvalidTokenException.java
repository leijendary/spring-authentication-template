package com.leijendary.spring.authenticationtemplate.exception;

import lombok.Getter;

public class InvalidTokenException extends RuntimeException {

    @Getter
    private final String source;

    public InvalidTokenException(final String source) {
        this.source = source;
    }
}
