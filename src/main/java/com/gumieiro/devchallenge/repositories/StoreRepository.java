package com.gumieiro.devchallenge.repositories;

import com.gumieiro.devchallenge.entities.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);
}
