package com.leijendary.spring.authenticationtemplate.error;

import com.leijendary.spring.authenticationtemplate.data.response.ErrorResponse;
import com.leijendary.spring.authenticationtemplate.exception.InvalidCredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.leijendary.spring.authenticationtemplate.util.RequestContext.getLocale;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Order(3)
@RequiredArgsConstructor
@Slf4j
public class InvalidCredentialExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidCredentialException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse catchInvalidCredential(final InvalidCredentialException exception) {
        log.error(exception.getMessage(), exception);

        final var code = "validation.credentials.invalid";
        final var message = messageSource.getMessage(code, new Object[] { }, getLocale());

        return ErrorResponse.builder()
                .addError(exception.getSource(), code, message)
                .status(NOT_FOUND)
                .build();
    }
}
