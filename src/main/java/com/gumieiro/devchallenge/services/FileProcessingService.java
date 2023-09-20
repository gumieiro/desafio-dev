package com.gumieiro.devchallenge.services;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileProcessingService {
    
    @Autowired
    private BlockingQueue<String> fileProcessingQueue;

    public void processFileAsync(String filepath) {
        try {
            log.info("Puting " + filepath + " to Queue");
            fileProcessingQueue.put(filepath);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error adding message to the queue", e);
        }
    }
}
