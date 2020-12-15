package com.specture.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties("elastic-index")
public class ElasticIndexTenantConfig {
    private Map<String, String> basicTenantIndexes;
    private Map<String, String> basicQosTenantIndexes;
}


