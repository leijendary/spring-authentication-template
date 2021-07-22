package com.leijendary.spring.authenticationtemplate.config;

import com.leijendary.spring.authenticationtemplate.config.properties.AspectProperties;
import com.leijendary.spring.authenticationtemplate.config.properties.AuthProperties;
import com.leijendary.spring.authenticationtemplate.config.properties.CorsProperties;
import com.leijendary.spring.authenticationtemplate.config.properties.InfoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AspectProperties.class,
        AuthProperties.class,
        CorsProperties.class,
        InfoProperties.class })
public class PropertiesConfiguration {
}
