package com.gumieiro.desafiodev.entities.mappers;

import com.gumieiro.desafiodev.dtos.TransactionDTO;
import com.gumieiro.desafiodev.entities.models.Store;
import com.gumieiro.desafiodev.entities.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface TransactionMapper {
    @Mapping(source = "store", target = "store")
    Transaction toModel(TransactionDTO dto);

    @Named("storeStrToStore")
    public static Store formatToStore(String storeStr) {
        // TODO: Create a service to get store
        return new Store();
    }
}
