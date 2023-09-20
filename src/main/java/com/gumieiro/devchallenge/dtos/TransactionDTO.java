package com.gumieiro.devchallenge.dtos;

import com.gumieiro.devchallenge.entities.enums.TransactionType;
import com.gumieiro.devchallenge.entities.models.Transaction;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Data
public class TransactionDTO {
    private TransactionType transactionType;
    private Date occurrenceDate;
    private Double value;
    private String documentNumber;
    private String creditCard;
    private Time time;
    private String storeRepresentantName;
    private String store;

    public TransactionDTO(String data) {
        int type = Integer.valueOf(data.substring(0, 0));
        setTransactionType(TransactionType.getByNumber(type));
        setOccurrenceDate(Date.valueOf(data.substring(1, 8)));

        String valueStr = Objects.requireNonNullElse(data.substring(9, 18), "0");
        Double parsedValue = Double.parseDouble(valueStr) / 100;
        setValue(parsedValue);
        setDocumentNumber(data.substring(19, 29));
        setCreditCard(data.substring(30, 41));
        setTime(Time.valueOf(data.substring(42, 47)));
        setStoreRepresentantName(data.substring(48, 61));
        setStore(data.substring(62, 81));
    }

    public Transaction toModel() {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(this.transactionType);
        transaction.setOccurrenceDate(new Date(this.occurrenceDate.getTime()));
        transaction.setValue(this.value);
        transaction.setDocumentNumber(this.documentNumber);
        transaction.setCreditCard(this.creditCard);
        transaction.setTime(this.time);
        transaction.setStoreRepresentantName(this.storeRepresentantName);
        return transaction;
    }
}
