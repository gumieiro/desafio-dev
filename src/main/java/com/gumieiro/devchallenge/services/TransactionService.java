package com.gumieiro.devchallenge.services;

import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.repositories.TransactionRepository;
import com.gumieiro.devchallenge.services.exceptions.DatabaseException;
import com.gumieiro.devchallenge.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

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

    public void delete(Long id) {
        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
}
