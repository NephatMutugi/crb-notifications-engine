package com.bpr.main.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @ Author NMuchiri
 **/
@Configuration
@Component
@EnableAsync
public class ExecutorConfig {
    @Value("${crb.thread-pool-size}")
    private int poolSize;
    @Bean(name = "myExecutor")
    public Executor myExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("CRB EXECUTOR-");
        executor.initialize();
        return executor;

    }
}
