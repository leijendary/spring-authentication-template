package com.leijendary.spring.authenticationtemplate.repository;

import com.leijendary.spring.authenticationtemplate.model.IamUserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IamUserCredentialRepository extends JpaRepository<IamUserCredential, Long> {

    Optional<IamUserCredential> findFirstByUsernameIgnoreCase(final String username);
}
