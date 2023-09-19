package com.gumieiro.desafiodev.dtos;

import com.gumieiro.desafiodev.entities.enums.TransactionType;
import lombok.Data;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;

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

    TransactionDTO(String data) throws Exception {
        if(data.length() != 81) throw new Exception("Invalid data source! The data is ");
        setTransactionType(TransactionType.valueOf(data.substring(0, 0)));

        Instant instant = Instant.parse(data.substring(1,8));
        setOccurrenceDate(Date.from(instant));

        String valueStr = data.substring(9, 18);
        Double parsedValue = Double.parseDouble(valueStr)/100;
        setValue(parsedValue);
        setDocumentNumber(data.substring(19, 29));
        setCreditCard(data.substring(30, 41));
        setTime(Time.valueOf(data.substring(42, 47)));
        setStoreRepresentantName(data.substring(48, 61));
        setStore(data.substring(62, 81));
    }
}
