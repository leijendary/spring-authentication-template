package com.leijendary.spring.authenticationtemplate.validator.v1;

import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.model.IamUserCredential;
import com.leijendary.spring.authenticationtemplate.repository.IamUserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.leijendary.spring.authenticationtemplate.util.ValidationUtil.rejectIfEmptyOrWhitespace;

@Component
@RequiredArgsConstructor
public class TokenRequestV1Validator implements Validator {

    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_AUDIENCE = "audience";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_DEVICE_ID = "deviceId";

    private final IamUserCredentialRepository iamUserCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(@NonNull Class<?> tClass) {
        return TokenRequestV1.class.equals(tClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        username(errors);
        password(errors);
        audience(errors);
        type(errors);
        deviceId(errors);
    }

    private void username(final Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_USERNAME);

        // Skip validation if there is already an error in the field
        if (errors.hasFieldErrors(FIELD_USERNAME)) {
            return;
        }

        final var value = (String) errors.getFieldValue(FIELD_USERNAME);
        final var credential = getCredential(value);

        if (credential.isEmpty()) {
            errors.rejectValue(FIELD_USERNAME, "validation.credentials.invalid");
        }
    }

    private void password(final Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_PASSWORD);

        // Skip validation if there is already an error in the field
        if (errors.hasFieldErrors(FIELD_PASSWORD) && !errors.hasFieldErrors(FIELD_PASSWORD)) {
            return;
        }

        final var username = (String) errors.getFieldValue(FIELD_USERNAME);
        final var value = (String) errors.getFieldValue(FIELD_PASSWORD);

        // Validate the password clear value and the encoded one
        getCredential(username).ifPresent((credential) -> {
            if (!passwordEncoder.matches(value, credential.getPassword())) {
                errors.rejectValue(FIELD_USERNAME, "validation.credentials.invalid");
            }
        });
    }

    private void audience(final Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_AUDIENCE);
    }

    private void type(final Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_TYPE);
    }

    private void deviceId(final Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_DEVICE_ID);
    }

    private Optional<IamUserCredential> getCredential(final String username) {
        return iamUserCredentialRepository.findFirstByUsernameIgnoreCase(username);
    }
}
