package com.gumieiro.devchallenge.entities.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gumieiro.devchallenge.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TransactionType transactionType;
    private Date occurrenceDate;
    private Double value;
    private String documentNumber;
    private String creditCard;
    private Time time;
    private String storeRepresentantName;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Double getValue() {
        return TransactionType.ENTRY.equals(getTransactionType().getType()) ? this.value : this.value * -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(transactionType, that.transactionType))
            return false;
        if (!Objects.equals(occurrenceDate, that.occurrenceDate))
            return false;
        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(documentNumber, that.documentNumber))
            return false;
        if (!Objects.equals(creditCard, that.creditCard)) return false;
        if (!Objects.equals(time, that.time)) return false;
        if (!Objects.equals(storeRepresentantName, that.storeRepresentantName))
            return false;
        return Objects.equals(store, that.store);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (occurrenceDate != null ? occurrenceDate.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (documentNumber != null ? documentNumber.hashCode() : 0);
        result = 31 * result + (creditCard != null ? creditCard.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (storeRepresentantName != null ? storeRepresentantName.hashCode() : 0);
        result = 31 * result + (store != null ? store.hashCode() : 0);
        return result;
    }
}
