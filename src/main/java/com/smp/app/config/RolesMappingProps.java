package com.smp.app.config;

import java.util.List;
import java.util.Set;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "user")
@PropertySource(value = "classpath:roles-mapping.yml", factory = YamlPropertySourceConfig.class)
public class RolesMappingProps {

    List<RoleToCapabilityMapping> roleToCapabilities;

    @Data
    public static class RoleToCapabilityMapping {

        private String roleName;
        private Set<String> allowedCapabilities;
    }
}
