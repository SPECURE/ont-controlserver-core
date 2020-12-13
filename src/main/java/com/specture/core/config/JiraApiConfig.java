package com.specture.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jira")
public class JiraApiConfig {
    private String requestTypeId;
    private String serviceDeskId;
    private String url;
    private String username;
    private String password;
}
