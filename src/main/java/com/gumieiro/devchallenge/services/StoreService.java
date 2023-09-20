package com.gumieiro.devchallenge.services;

import com.gumieiro.devchallenge.entities.models.Store;
import com.gumieiro.devchallenge.repositories.StoreRepository;
import com.gumieiro.devchallenge.services.exceptions.DatabaseException;
import com.gumieiro.devchallenge.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class StoreService {
    @Autowired
    StoreRepository storeRepository;

    public Store findByName(String name) {
        return Objects.requireNonNullElse(storeRepository.findByName(name), new Store(name));
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findById(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        return store.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Store insert(Store obj) {
        return storeRepository.save(obj);
    }

    public List<Store> insertAll(List<Store> objList) {
        log.info("Inserting " + objList.size() + " stores in DB");
        return storeRepository.saveAll(objList);
    }

    public void delete(Long id) {
        try {
            storeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Store update(Long id, Store obj) {
        try {
            Store entity = storeRepository.getReferenceById(id);
            updateData(entity, obj);
            return storeRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateData(Store entity, Store obj) {
        entity.setName(obj.getName());
    }
}
