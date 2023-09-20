package com.gumieiro.devchallenge.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileProcessingService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public FileProcessingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processFileAsync(String filepath) {
        rabbitTemplate.convertAndSend("importing", filepath);
    }
}
