package com.leijendary.spring.authenticationtemplate.error;

import com.leijendary.spring.authenticationtemplate.data.response.ErrorResponse;
import com.leijendary.spring.authenticationtemplate.exception.NotActiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.getLocale;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Order(3)
@RequiredArgsConstructor
public class NotActiveExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(NotActiveException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse catchNotActive(final NotActiveException exception) {
        final var code = exception.getCode();
        final var message = messageSource.getMessage(code, new Object[] { }, getLocale());

        return ErrorResponse.builder()
                .addError(exception.getResource(), code, message)
                .status(NOT_FOUND)
                .build();
    }
}
