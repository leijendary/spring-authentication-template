package com.leijendary.spring.authenticationtemplate.error;

import com.leijendary.spring.authenticationtemplate.data.response.ErrorResponse;
import com.leijendary.spring.authenticationtemplate.exception.InvalidTokenSignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.leijendary.spring.authenticationtemplate.util.RequestContext.getLocale;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Order(3)
@RequiredArgsConstructor
public class InvalidTokenSignatureExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidTokenSignatureException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse catchInvalidTokenSignature(final InvalidTokenSignatureException exception) {
        final var code = "validation.token.invalid.signature";
        final var message = messageSource.getMessage(code, new Object[] { }, getLocale());

        return ErrorResponse.builder()
                .addError(exception.getSource(), code, message)
                .status(BAD_REQUEST)
                .build();
    }
}
