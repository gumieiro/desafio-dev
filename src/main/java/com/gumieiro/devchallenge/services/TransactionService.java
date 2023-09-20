package com.gumieiro.devchallenge.services;

import com.gumieiro.devchallenge.config.AppConfig;
import com.gumieiro.devchallenge.dtos.TransactionDTO;
import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.repositories.TransactionRepository;
import com.gumieiro.devchallenge.services.exceptions.DatabaseException;
import com.gumieiro.devchallenge.services.exceptions.GeneralException;
import com.gumieiro.devchallenge.services.exceptions.ResourceNotFoundException;
import com.gumieiro.devchallenge.services.exceptions.ServiceException;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private FileProcessingService fileProcessingService;

    @Autowired
    private AppConfig config;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Transaction insert(Transaction obj) {
        return transactionRepository.save(obj);
    }

    public List<Transaction> insertAll(List<Transaction> objList) {
        log.info("Inserting " + objList.size() + " Transactions in DB");
        return transactionRepository.saveAll(objList);
    }

    public void delete(Long id) {
        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    public Transaction update(Long id, Transaction obj) {
        try {
            Transaction entity = transactionRepository.getReferenceById(id);
            updateData(entity, obj);
            return transactionRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    private void updateData(Transaction entity, Transaction obj) {
        entity.setCreditCard(obj.getCreditCard());
        entity.setTransactionType(obj.getTransactionType());
        entity.setOccurrenceDate(obj.getOccurrenceDate());
        entity.setValue(obj.getValue());
        entity.setDocumentNumber(obj.getDocumentNumber());
        entity.setCreditCard(obj.getCreditCard());
        entity.setTime(obj.getTime());
        entity.setStoreRepresentantName(obj.getStoreRepresentantName());
        entity.setStore(obj.getStore());
    }

    public String importCNAB(MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error-message", "File without data! Import a valid file.");
            return "redirect:/transaction/import";
        }

        // Verifique se o tipo do arquivo é TXT
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            redirectAttributes.addFlashAttribute("error-message", "Somente arquivos de texto (.txt) são permitidos.");
            return "redirect:/transaction/import";
        }

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(config.getUploadDir()); // Substitua pelo caminho desejado
            Path filePath = uploadPath.resolve(fileName);
            log.info("Importing file " + fileName);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream();
                    OutputStream outputStream = Files.newOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            log.info("File imported to " + filePath);
            redirectAttributes.addFlashAttribute("success-message",
                    "The file was uploading successfully and will be imported.");
            fileProcessingService.processFileAsync(filePath.toString());
            return "redirect:/stores";
        } catch (Exception e) {
            throw new GeneralException(e);
        }
    }

    public List<Transaction> strListToModel(List<String> transactions) {
        log.info("Reading data and transforming to Transaction Object");
        return transactions.stream()
                .map(TransactionDTO::new)
                .map(TransactionDTO::toModel)
                .toList();
    }
}
