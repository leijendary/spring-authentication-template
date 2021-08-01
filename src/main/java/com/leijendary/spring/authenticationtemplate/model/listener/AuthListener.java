package com.leijendary.spring.authenticationtemplate.model.listener;

import com.leijendary.spring.authenticationtemplate.event.producer.AuthProducer;
import com.leijendary.spring.authenticationtemplate.model.Auth;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import static com.leijendary.spring.authenticationtemplate.factory.AuthFactory.toSchema;
import static com.leijendary.spring.authenticationtemplate.util.SpringContext.getBean;

public class AuthListener {

    @PostPersist
    public void onSave(final Auth auth) {
        final var authProducer = getBean(AuthProducer.class);
        final var authSchema = toSchema(auth);

        authProducer.create(authSchema);
    }

    @PostUpdate
    public void onUpdate(final Auth auth) {
        final var authProducer = getBean(AuthProducer.class);
        final var authSchema = toSchema(auth);

        authProducer.refresh(authSchema);
    }

    @PostRemove
    public void onDelete(final Auth auth) {
        final var authProducer = getBean(AuthProducer.class);
        final var authSchema = toSchema(auth);

        authProducer.revoke(authSchema);
    }
}
