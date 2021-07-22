package com.leijendary.spring.authenticationtemplate.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthAccess extends IdentityIdModel {

    @OneToOne
    private Auth auth;

    private String token;
    private Instant expiryDate;
}

