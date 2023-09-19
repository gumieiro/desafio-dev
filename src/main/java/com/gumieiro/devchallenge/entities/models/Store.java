package com.gumieiro.devchallenge.entities.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "store")
    private List<Transaction> transactions = new ArrayList<>();

    public Store(String name) {
        setName(name);
    }

    public Double balance() {
        return this.transactions.stream().mapToDouble(Transaction::getValue).sum();
    }
}
