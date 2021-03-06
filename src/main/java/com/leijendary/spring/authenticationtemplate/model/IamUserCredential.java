package com.leijendary.spring.authenticationtemplate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class IamUserCredential extends IdentityIdModel {

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "iam_user_id")
    private IamUser user;

    private String username;
    private String password;
    private String type;

    @CreatedDate
    private OffsetDateTime createdDate;

    private OffsetDateTime lastUsedDate;
}
