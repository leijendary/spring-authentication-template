package com.leijendary.spring.authenticationtemplate.config;

import com.leijendary.spring.authenticationtemplate.config.properties.InfoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.CompletableFuture;

import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final InfoProperties infoProperties;

    @Bean
    public Docket api() {
        return new Docket(OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .genericModelSubstitutes(CompletableFuture.class, ResponseEntity.class);
    }

    private ApiInfo apiInfo() {
        final var app = infoProperties.getApp();
        final var api = infoProperties.getApi();

        return new ApiInfo(
                app.getName(),
                app.getDescription(),
                app.getVersion(),
                api.getTermsOfServiceUrl(),
                api.getContact(),
                api.getLicense(),
                api.getLicenseUrl(),
                api.getVendorExtensions());
    }
}
