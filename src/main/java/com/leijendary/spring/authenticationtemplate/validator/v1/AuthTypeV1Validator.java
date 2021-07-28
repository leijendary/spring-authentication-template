package com.leijendary.spring.authenticationtemplate.validator.v1;

import com.leijendary.spring.authenticationtemplate.validator.annotation.v1.AuthTypeV1;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static com.leijendary.spring.authenticationtemplate.data.AuthType.EMAIL_ADDRESS;
import static com.leijendary.spring.authenticationtemplate.data.AuthType.MOBILE_NUMBER;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

public class AuthTypeV1Validator implements ConstraintValidator<AuthTypeV1, String> {

    private static final List<String> VALID_TYPES = asList(EMAIL_ADDRESS, MOBILE_NUMBER);

    @Override
    public void initialize(final AuthTypeV1 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return ofNullable(value)
                .map(VALID_TYPES::contains)
                .orElse(false);
    }
}
