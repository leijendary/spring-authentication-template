package com.leijendary.spring.authenticationtemplate.repository;

import com.leijendary.spring.authenticationtemplate.model.IamUserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IamUserCredentialRepository extends JpaRepository<IamUserCredential, Long>,
        JpaSpecificationExecutor<IamUserCredential> {
}
