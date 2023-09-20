package com.gumieiro.devchallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class AppConfig {
    @Value("${upload.dir}")
    private String uploadDir;
}
