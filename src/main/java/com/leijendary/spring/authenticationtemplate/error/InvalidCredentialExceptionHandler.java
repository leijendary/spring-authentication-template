package com.leijendary.spring.authenticationtemplate.error;

import com.leijendary.spring.authenticationtemplate.data.response.ErrorResponse;
import com.leijendary.spring.authenticationtemplate.exception.InvalidCredentialException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Order(3)
@RequiredArgsConstructor
public class InvalidCredentialExceptionHandler {

    @ExceptionHandler(InvalidCredentialException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse catchInvalidCredential(final InvalidCredentialException exception) {
        final var code = "validation.credentials.invalid";

        return ErrorResponse.builder()
                .addError(exception.getSource(), code, exception.getCredential())
                .status(NOT_FOUND)
                .build();
    }
}
