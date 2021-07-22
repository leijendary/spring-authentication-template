package com.leijendary.spring.authenticationtemplate.event.consumer;

import com.leijendary.spring.authenticationtemplate.event.schema.UserSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    @Bean
    public Consumer<KStream<String, UserSchema>> userCreated() {
        return stream -> stream.foreach((key, value) -> log.info("Created: '{}', '{}'", key, value));
    }

    @Bean
    public Consumer<KStream<String, UserSchema>> userUpdated() {
        return stream -> stream.foreach((key, value) -> log.info("Updated: '{}', '{}'", key, value));
    }
}
