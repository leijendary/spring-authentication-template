package com.leijendary.spring.authenticationtemplate.repository;

import com.leijendary.spring.authenticationtemplate.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findFirstByAccessId(final UUID accessId);

    Optional<Auth> findFirstByRefreshId(final UUID refreshId);
}
