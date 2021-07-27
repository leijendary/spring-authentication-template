package com.leijendary.spring.authenticationtemplate.validator.annotation.v1;


import com.leijendary.spring.authenticationtemplate.validator.v1.AuthTypeV1Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AuthTypeV1Validator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthTypeV1 {

    String message() default "validation.auth.type.invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
