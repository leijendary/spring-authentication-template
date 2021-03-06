package com.leijendary.spring.authenticationtemplate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class IamRole extends IdentityIdModel {

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "iam_role_permission",
            joinColumns = @JoinColumn(name = "iam_role_id"),
            inverseJoinColumns = @JoinColumn(name = "iam_permission_id"))
    private Set<IamPermission> permissions;

    @CreatedDate
    private OffsetDateTime createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private OffsetDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
}
