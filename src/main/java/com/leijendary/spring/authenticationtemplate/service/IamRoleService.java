package com.leijendary.spring.authenticationtemplate.service;

import com.leijendary.spring.authenticationtemplate.model.IamPermission;
import com.leijendary.spring.authenticationtemplate.model.IamRole;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class IamRoleService extends AbstractService {

    public Set<String> getScopes(final IamRole iamRole) {
        final var permissions = ofNullable(iamRole)
                .map(IamRole::getPermissions)
                .orElse(new HashSet<>());

        return formatToScope(permissions);
    }

    private Set<String> formatToScope(final Set<IamPermission> permissions) {
        return permissions
                .stream()
                .map(IamPermission::getPermission)
                .collect(Collectors.toSet());
    }
}
