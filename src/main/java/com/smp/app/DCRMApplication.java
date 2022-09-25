package com.smp.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smp.app.config.CustomDateDeserializer;
import java.time.Duration;
import java.util.Date;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableScheduling
public class DCRMApplication extends SpringBootServletInitializer {

    static final int TIMEOUT = 300000;

    public static void main(String[] args) {
        SpringApplication.run(DCRMApplication.class, args);
    }

    @Bean
    RestTemplate restTemplateWithConnectReadTimeout() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(TIMEOUT))
            .setReadTimeout(Duration.ofMillis(TIMEOUT)).build();
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine engine = new VelocityEngine();

        engine.setProperty("resource.loader", "class");
        engine.setProperty("class.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        return engine;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule simpleModule = new JavaTimeModule();
        simpleModule.addDeserializer(Date.class, new CustomDateDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("DCRMMessages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DCRMApplication.class);
    }
}