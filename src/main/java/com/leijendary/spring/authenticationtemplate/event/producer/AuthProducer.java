package com.leijendary.spring.authenticationtemplate.event.producer;

import com.leijendary.spring.authenticationtemplate.event.schema.AuthSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

import static reactor.core.publisher.Sinks.many;

@Component
public class AuthProducer extends AbstractProducer<AuthSchema> {

    private final Sinks.Many<Message<AuthSchema>> createBuffer = many().multicast().onBackpressureBuffer();
    private final Sinks.Many<Message<AuthSchema>> refreshBuffer = many().multicast().onBackpressureBuffer();
    private final Sinks.Many<Message<AuthSchema>> revokeBuffer = many().multicast().onBackpressureBuffer();

    @Bean
    public Supplier<Flux<Message<AuthSchema>>> tokenCreate() {
        return createBuffer::asFlux;
    }

    @Bean
    public Supplier<Flux<Message<AuthSchema>>> tokenRefresh() {
        return refreshBuffer::asFlux;
    }

    @Bean
    public Supplier<Flux<Message<AuthSchema>>> tokenRevoke() {
        return revokeBuffer::asFlux;
    }

    public void create(final AuthSchema authSchema) {
       final var message = messageWithKey(String.valueOf(authSchema.getId()), authSchema);

       createBuffer.tryEmitNext(message);
    }

    public void refresh(final AuthSchema authSchema) {
        final var message = messageWithKey(String.valueOf(authSchema.getId()), authSchema);

        refreshBuffer.tryEmitNext(message);
    }

    public void revoke(final AuthSchema authSchema) {
        final var message = messageWithKey(String.valueOf(authSchema.getId()), authSchema);

        revokeBuffer.tryEmitNext(message);
    }
}
