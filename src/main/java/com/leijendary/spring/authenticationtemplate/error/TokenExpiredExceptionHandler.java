package com.leijendary.spring.authenticationtemplate.error;

import com.leijendary.spring.authenticationtemplate.data.response.ErrorResponse;
import com.leijendary.spring.authenticationtemplate.exception.TokenExpiredException;
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
public class TokenExpiredExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse catchTokenExpired(final TokenExpiredException exception) {
        final var code = "validation.token.expired";
        final var message = messageSource.getMessage(code, new Object[] { }, getLocale());

        return ErrorResponse.builder()
                .addError(exception.getSource(), code, message)
                .status(BAD_REQUEST)
                .build();
    }
}
