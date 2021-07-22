package com.leijendary.spring.authenticationtemplate.exception;

import lombok.Getter;

public class NotActiveException extends RuntimeException {

    @Getter
    private final String resource;

    @Getter
    private final String code;

    public NotActiveException(final String resource, final String code) {
        super("Not Active");

        this.resource = resource;
        this.code = code;
    }
}
