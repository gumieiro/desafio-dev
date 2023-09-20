package com.gumieiro.devchallenge.services;

import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.repositories.TransactionRepository;
import com.gumieiro.devchallenge.services.exceptions.DatabaseException;
import com.gumieiro.devchallenge.services.exceptions.ResourceNotFoundException;
import com.gumieiro.devchallenge.services.exceptions.ServiceException;
import jakarta.persistence.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private FileProcessingService fileProcessingService;

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

    public Boolean insertAll(List<Transaction> transactions) {
        Boolean result = Boolean.FALSE;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-persistence-unit-name");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (Transaction transaction : transactions) {
                em.persist(transaction.getStore());
                em.persist(transaction);
            }
            tx.commit();
            result = Boolean.TRUE;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ServiceException(e);
        } finally {
            em.close();
            emf.close();
        }
        return result;
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

    public boolean importCNAB(MultipartFile file) throws Exception {
        if (file.isEmpty()) throw new FileUploadException("File without data! Import a valid file!");
        try {
            String filepath = "/files/" + file.getOriginalFilename();
            Path filePath = Paths.get(filepath);
            Files.write(filePath, file.getBytes());
            fileProcessingService.processFileAsync(filepath);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
