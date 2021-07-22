package com.leijendary.spring.authenticationtemplate.exception;

import lombok.Getter;

public class InvalidCredentialException extends RuntimeException {

    @Getter
    private final String credential;

    @Getter
    private final String source;

    public InvalidCredentialException(final String credential, final String source) {
        super(String.format("Credential for %s was not found", credential));

        this.credential = credential;
        this.source = source;
    }
}
