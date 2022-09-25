package com.smp.app.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class SwaggerConfig {

    @Bean
    public Set apis(SwaggerUiConfigProperties swaggerUiConfig) {
        Set swaggerUrlSet = new HashSet<>();
        swaggerUrlSet.add(new SwaggerUrl("Superadmin", "/swagger_superadmin.yaml", "Backend-SuperAdmin Specific"));
        swaggerUrlSet.add(new SwaggerUrl("Other", "/swagger_other.yaml", "Backend- Other Roles"));
        swaggerUiConfig.setUrls(swaggerUrlSet);
        return swaggerUrlSet;
    }

    @Bean
    public MappingJackson2HttpMessageConverter octetStreamJsonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "octet-stream")));
        return converter;
    }

}
