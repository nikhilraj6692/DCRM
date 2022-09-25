package com.smp.app.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")
@PropertySource(value = "classpath:permissions-mapping.yml", factory = YamlPropertySourceConfig.class)
public class ApiToPermissionProps {

    Map<String, PermissionProps> metaData;

    @Data
    public static class PermissionProps {

        private String method;
        private String capabilityId;
        private boolean statusCheck;
    }
}
