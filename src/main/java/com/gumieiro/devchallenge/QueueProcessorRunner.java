package com.gumieiro.devchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gumieiro.devchallenge.services.consumers.FileImportingConsumer;

@Component
public class QueueProcessorRunner implements CommandLineRunner {
    
    @Autowired
    private FileImportingConsumer fileImportingConsumer;

    @Override
    public void run(String... args) {
        // Start the queue processing
        fileImportingConsumer.processMessages();
    }

}
