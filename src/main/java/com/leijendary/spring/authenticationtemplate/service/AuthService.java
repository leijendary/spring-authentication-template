package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.data.AuthData;
import com.leijendary.spring.authenticationtemplate.factory.AuthFactory;
import com.leijendary.spring.authenticationtemplate.model.Auth;
import com.leijendary.spring.authenticationtemplate.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.leijendary.spring.authenticationtemplate.util.RequestContextUtil.now;

@Service
@RequiredArgsConstructor
public class AuthService extends AbstractService {

    private final AuthRepository authRepository;

    public Auth create(final AuthData authData) {
        final var auth = AuthFactory.of(authData);
        auth.setCreatedDate(now());

        return authRepository.save(auth);
    }
}
