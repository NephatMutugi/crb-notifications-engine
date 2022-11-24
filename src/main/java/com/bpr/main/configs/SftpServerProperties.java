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
@ConfigurationProperties("sftp.server")
public class SftpServerProperties {
    private String host;
    private String username;
    private String password;
    private String filepath;
}
