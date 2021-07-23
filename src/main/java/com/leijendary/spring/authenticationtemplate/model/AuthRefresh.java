package com.leijendary.spring.authenticationtemplate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthRefresh {

    @Id
    private UUID id;

    @OneToOne
    private Auth auth;

    private UUID accessTokenId;
    private String token;
    private Instant expiryDate;
}
