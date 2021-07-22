package com.leijendary.spring.authenticationtemplate.repository;

import com.leijendary.spring.authenticationtemplate.model.IamUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IamUserRepository extends JpaRepository<IamUser, Long> {
}
