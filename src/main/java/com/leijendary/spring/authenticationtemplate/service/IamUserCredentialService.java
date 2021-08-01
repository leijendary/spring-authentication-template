package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.CredentialData;
import com.leijendary.spring.authenticationtemplate.exception.InvalidCredentialException;
import com.leijendary.spring.authenticationtemplate.model.IamUserCredential;
import com.leijendary.spring.authenticationtemplate.repository.IamUserCredentialRepository;
import com.leijendary.spring.authenticationtemplate.specification.NonDeactivatedAccountUserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.now;

@Service
@RequiredArgsConstructor
public class IamUserCredentialService extends AbstractService {

    private final IamUserCredentialRepository iamUserCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public IamUserCredential matchPassword(final CredentialData credentialData) {
        final var username = credentialData.getUsername();
        final var password = credentialData.getPassword();
        final var type = credentialData.getType();
        final var specification = NonDeactivatedAccountUserCredential.builder()
                .username(username)
                .type(type)
                .build();
        final var iamUserCredential = iamUserCredentialRepository.findOne(specification)
                .orElseThrow(() -> new InvalidCredentialException(username, "username"));

        // Validate password if the plain text password matches the encoded password
        // from the database
        if (!passwordEncoder.matches(password, iamUserCredential.getPassword())) {
            throw new InvalidCredentialException(username, "username");
        }

        // Update the last used date of the credential to current timestamp
        // since this is technically used right now
        iamUserCredential.setLastUsedDate(now());

        return iamUserCredentialRepository.save(iamUserCredential);
    }
}
