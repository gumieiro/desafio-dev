package com.gumieiro.devchallenge.entities.mappers;

import com.gumieiro.devchallenge.dtos.TransactionDTO;
import com.gumieiro.devchallenge.entities.models.Store;
import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.services.StoreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;


@Mapper
public interface TransactionMapper {
    @Named("storeStrToStoreObj")
    static Store storeStrToStoreObj(String storeStr) {
        return new StoreService().findByName(storeStr);
    }

    @Mapping(source = "store", target = "store", qualifiedByName = "storeStrToStoreObj", resultType = Store.class)
    Transaction toModel(TransactionDTO dto);
}
