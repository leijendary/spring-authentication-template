package com.leijendary.spring.authenticationtemplate.model;

import com.leijendary.spring.authenticationtemplate.model.listener.AuthListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners({ AuditingEntityListener.class, AuthListener.class })
public class Auth extends SnowflakeIdModel {

    private long userId;
    private String username;
    private String audience;
    private String type;
    private String deviceId;

    @CreatedDate
    private OffsetDateTime createdDate;

    @ToString.Exclude
    @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL)
    private AuthAccess access;

    @ToString.Exclude
    @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL)
    private AuthRefresh refresh;

    public static class Type {

        public static final String MOBILE = "mobile";
        public static final String EMAIL = "email";
    }
}
