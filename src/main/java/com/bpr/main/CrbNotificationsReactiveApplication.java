package com.bpr.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrbNotificationsReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrbNotificationsReactiveApplication.class, args);
    }

}
