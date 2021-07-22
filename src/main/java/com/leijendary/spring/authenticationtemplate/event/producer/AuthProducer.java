package com.leijendary.spring.authenticationtemplate.event.producer;

import com.leijendary.spring.authenticationtemplate.event.schema.AuthSchema;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import static com.leijendary.spring.authenticationtemplate.event.binding.TokenBinding.*;

@Component
public class AuthProducer extends AbstractProducer<AuthSchema> {

    public AuthProducer(final StreamBridge streamBridge) {
        super(streamBridge);
    }

    public void created(final AuthSchema tokenSchema) {
        send(CREATED, String.valueOf(tokenSchema.getId()), tokenSchema);
    }

    public void refreshed(final AuthSchema tokenSchema) {
        send(REFRESHED, String.valueOf(tokenSchema.getId()), tokenSchema);
    }

    public void revoked(final AuthSchema tokenSchema) {
        send(REVOKED, String.valueOf(tokenSchema.getId()), tokenSchema);
    }
}
