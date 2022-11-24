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
@ConfigurationProperties("t24.crb-template")
public class T24Properties {
    private String aaConsumer;
    private String aaCorporate;
    private String ldConsumer;
    private String ldCorporate;
    private String odConsumer;
    private String odCorporate;
    private String pdConsumer;
    private String pdCorporate;
}
