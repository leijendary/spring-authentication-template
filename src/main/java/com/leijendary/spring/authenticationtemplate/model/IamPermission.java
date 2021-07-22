package com.leijendary.spring.authenticationtemplate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class IamPermission extends IdentityIdModel {

    private String permission;
}
