package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.exception.NotActiveException;
import com.leijendary.spring.authenticationtemplate.exception.ResourceNotFoundException;
import com.leijendary.spring.authenticationtemplate.model.IamUser;
import com.leijendary.spring.authenticationtemplate.repository.IamUserRepository;
import com.leijendary.spring.authenticationtemplate.specification.NonDeactivatedAccountUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.leijendary.spring.authenticationtemplate.data.Status.ACTIVE;
import static com.leijendary.spring.authenticationtemplate.data.Status.INCOMPLETE;
import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class IamUserService extends AbstractService {

    private final IamUserRepository iamUserRepository;

    public IamUser get(final long id) {
        final var specification = NonDeactivatedAccountUser.builder()
                .userId(id)
                .build();

        return iamUserRepository.findOne(specification)
                .orElseThrow(() -> new ResourceNotFoundException("user", id));
    }

    public void checkUserStatus(final IamUser user) {
        // Valid login statuses
        final var statuses = asList(ACTIVE, INCOMPLETE);

        if (!statuses.contains(user.getStatus())) {
            throw new NotActiveException("user.status", "access.user.inactive");
        }
    }
}
