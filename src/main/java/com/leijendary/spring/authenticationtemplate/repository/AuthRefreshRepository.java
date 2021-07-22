package com.leijendary.spring.authenticationtemplate.repository;

import com.leijendary.spring.authenticationtemplate.model.AuthRefresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRefreshRepository extends JpaRepository<AuthRefresh, Long> {
}
