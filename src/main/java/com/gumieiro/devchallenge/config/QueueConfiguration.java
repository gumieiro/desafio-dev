package com.gumieiro.devchallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfiguration {

    @Bean
    public BlockingQueue<String> fileProcessingQueue() {
        return new LinkedBlockingQueue<>();
    }
}
