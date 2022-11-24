package com.bpr.main.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ Author NMuchiri
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties("crb")
public class CustomProperties {
    private String baseUrl;
    private String tokenBaseResource;
    private String notificationsBaseResource;
    private String username;
    private String password;
    private String infinityCode;
    private String notificationsBaseResourceCorporate;
}
