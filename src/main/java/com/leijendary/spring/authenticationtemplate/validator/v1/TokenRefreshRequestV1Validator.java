package com.leijendary.spring.authenticationtemplate.validator.v1;

import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Instant;

import static com.leijendary.spring.authenticationtemplate.util.ValidationUtil.rejectIfEmptyOrWhitespace;

@Component
@RequiredArgsConstructor
public class TokenRefreshRequestV1Validator implements Validator {

    private static final String FIELD_REFRESH_TOKEN = "refreshToken";

    private final AuthRepository authRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return TokenRefreshRequestV1.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        refreshToken(errors);
    }

    private void refreshToken(final Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_REFRESH_TOKEN);

        // Skip validation if there is already an error in the field
        if (errors.hasFieldErrors(FIELD_REFRESH_TOKEN)) {
            return;
        }

        final var value = (String) errors.getFieldValue(FIELD_REFRESH_TOKEN);
        final var auth = authRepository.findFirstByRefreshToken(value);

        if (auth.isEmpty()) {
            errors.rejectValue(FIELD_REFRESH_TOKEN, "validation.refreshToken.invalid");

            return;
        }

        final var expiryDate = auth.get().getRefresh().getExpiryDate();

        if (expiryDate.isBefore(Instant.now())) {
            errors.rejectValue(FIELD_REFRESH_TOKEN, "validation.refreshToken.expired");
        }
    }
}
