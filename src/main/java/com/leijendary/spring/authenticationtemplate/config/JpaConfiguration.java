package com.leijendary.spring.authenticationtemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.leijendary.spring.authenticationtemplate.util.RequestContext.now;
import static java.util.Optional.of;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class JpaConfiguration {

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> of(now());
    }
}
