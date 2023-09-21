package com.gumieiro.devchallenge.dtos;

import com.gumieiro.devchallenge.entities.enums.TransactionType;
import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.utils.UtilDateTime;

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
        int type = Integer.valueOf(data.substring(0, 1));
        setTransactionType(TransactionType.getByNumber(type));
        Date date = UtilDateTime.strToSqlDate(data.substring(1, 9));
        setOccurrenceDate(date);

        String valueStr = Objects.requireNonNullElse(data.substring(9, 19), "0");
        Double parsedValue = Double.parseDouble(valueStr) / 100;
        setValue(parsedValue);
        setDocumentNumber(data.substring(19, 30));
        setCreditCard(data.substring(30, 42));
        setTime(UtilDateTime.strToSqlTime(data.substring(42, 48)));
        setStoreRepresentantName(data.substring(48, 62));
        setStore(data.substring(62, 80));
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
        transaction.setStore(store);
        return transaction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TransactionDTO: ");
        
        sb.append("\ngetTransactionType: " + getTransactionType());
        sb.append("\ngetOccurrenceDate: " + getOccurrenceDate());
        sb.append("\ngetValue: " + getValue());
        sb.append("\ngetDocumentNumber: " + getDocumentNumber());
        sb.append("\ngetCreditCard: " + getCreditCard());
        sb.append("\ngetTime: " + getTime());
        sb.append("\ngetStoreRepresentantName: " + getStoreRepresentantName());
        return sb.toString();
    }
}
