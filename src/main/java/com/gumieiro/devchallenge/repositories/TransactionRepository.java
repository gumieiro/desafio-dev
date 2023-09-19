package com.gumieiro.devchallenge.repositories;

import com.gumieiro.devchallenge.entities.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


}
