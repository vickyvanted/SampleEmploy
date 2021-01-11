package com.employee.smacs.service.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;
 
@Configuration
@ComponentScan( value = { "com.employee.smacs.service.async" }, lazyInit=true)
@EnableAsync
@Slf4j
public class AsyncConfig
{
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
    	log.info("Executor asyncExecutor - Initiated");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }
}
