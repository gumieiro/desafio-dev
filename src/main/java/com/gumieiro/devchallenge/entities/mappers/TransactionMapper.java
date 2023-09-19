package com.gumieiro.devchallenge.entities.mappers;

import com.gumieiro.devchallenge.dtos.TransactionDTO;
import com.gumieiro.devchallenge.entities.models.Store;
import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.repositories.StoreRepository;
import com.gumieiro.devchallenge.services.StoreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Mapper
public interface TransactionMapper {
    @Mapping(source = "store", target = "store")
    Transaction toModel(TransactionDTO dto);

    @Named("storeStrToStore")
    public static Store formatToStore(String storeStr) {
        return new StoreService().findByName(storeStr);
    }
}
