package com.gumieiro.devchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.gumieiro.devchallenge.*")
@ComponentScan(basePackages = "com.gumieiro.devchallenge.*")
@EntityScan(basePackages = "com.gumieiro.devchallenge.*")
@EnableJpaRepositories(basePackages = "com.gumieiro.devchallenge.*")
public class DevChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevChallengeApplication.class, args);
    }
}
