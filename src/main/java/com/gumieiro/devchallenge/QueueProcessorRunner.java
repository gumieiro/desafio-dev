package com.gumieiro.devchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gumieiro.devchallenge.services.consumers.FileImportingConsumer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueProcessorRunner implements CommandLineRunner {
    
    @Autowired
    private FileImportingConsumer fileImportingConsumer;

    @Override
    public void run(String... args) {
        // Start the queue processing
        log.info("STARTING QUEUES");
        fileImportingConsumer.processFileProcessingQueue();
    }

}
