package com.gumieiro.devchallenge.queues;

import com.gumieiro.devchallenge.dtos.TransactionDTO;
import com.gumieiro.devchallenge.entities.mappers.TransactionMapper;
import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.services.TransactionService;
import com.gumieiro.devchallenge.services.exceptions.ServiceException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileImportingReceiver {
    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    TransactionService service;

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

    @RabbitListener(queues = "importing")
    public void handleMessage(String filepath) {
        try {
            List<String> lines = readFileAsListOfStrings(filepath);

            List<Transaction> transactions = lines.stream()
                    .map(TransactionDTO::new)
                    .map(transactionMapper::toModel)
                    .toList();

            service.insertAll(transactions);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
