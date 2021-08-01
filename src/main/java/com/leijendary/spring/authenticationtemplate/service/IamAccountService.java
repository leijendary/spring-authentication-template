package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.exception.NotActiveException;
import com.leijendary.spring.authenticationtemplate.model.IamAccount;
import org.springframework.stereotype.Service;

import static com.leijendary.spring.authenticationtemplate.data.Status.ACTIVE;

@Service
public class IamAccountService extends AbstractService {

    public void checkAccountStatus(final IamAccount account) {
        // "active" is the only valid status for account when logging in
        if (account != null && !account.getStatus().equals(ACTIVE)) {
            throw new NotActiveException("account.status", "access.account.inactive");
        }
    }
}
