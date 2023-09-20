package com.gumieiro.devchallenge.services.consumers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gumieiro.devchallenge.entities.models.Store;
import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.services.StoreService;
import com.gumieiro.devchallenge.services.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileImportingConsumer {
    @Autowired
    private BlockingQueue<String> fileProcessingQueue;

    @Autowired
    TransactionService transactionService;

    @Autowired
    StoreService storeService;

    public void processFileProcessingQueue() {
        while (true) {
            log.info("Start queue processing at " + Instant.now());
            try {
                String file = fileProcessingQueue.take(); 
                List<String> lines = readFileAsListOfStrings(file);
                List<Transaction> transactions = transactionService.strListToModel(lines);

                List<Store> stores = transactions.stream()
                .map(x -> x.getStore())
                .toList();

                stores = storeService.insertAll(stores);
                log.info(stores.size()  + " stores inserted");

                transactions = transactionService.insertAll(transactions);
                log.info(transactions.size()  + " transactions inserted");

                log.info("Stoping queue processing at " + Instant.now());
                Thread.sleep(1000*5);
            } catch (InterruptedException | IOException e) {
                log.info("Error: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static List<String> readFileAsListOfStrings(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        // Abra o arquivo para leitura
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Leia cada linha do arquivo e adicione à lista
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }
}