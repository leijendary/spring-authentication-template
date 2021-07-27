package com.leijendary.spring.authenticationtemplate.error;

import com.leijendary.spring.authenticationtemplate.data.response.ErrorResponse;
import com.leijendary.spring.authenticationtemplate.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.getLocale;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Order(3)
@RequiredArgsConstructor
public class InvalidTokenExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse catchInvalidToken(final InvalidTokenException exception) {
        final var code = "validation.token.invalid";
        final var message = messageSource.getMessage(code, new Object[] { }, getLocale());

        return ErrorResponse.builder()
                .addError(exception.getSource(), code, message)
                .status(BAD_REQUEST)
                .build();
    }
}
