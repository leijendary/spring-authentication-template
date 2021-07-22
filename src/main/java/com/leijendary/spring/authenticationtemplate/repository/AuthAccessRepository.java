package com.leijendary.spring.authenticationtemplate.repository;

import com.leijendary.spring.authenticationtemplate.model.AuthAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthAccessRepository extends JpaRepository<AuthAccess, Long> {
}
