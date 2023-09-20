package com.gumieiro.devchallenge.entities.enums;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEBIT(1, "Debit", TransactionType.ENTRY, '+'),
    TICKET(2, "Ticket", TransactionType.OUTPUT, '-'),
    FINANCING(3, "Financing", TransactionType.OUTPUT, '+'),
    CREDIT(4, "Credit", TransactionType.ENTRY, '+'),
    LOAN(5, "Receiving Loan", TransactionType.ENTRY, '+'),
    SALES(6, "Sales", TransactionType.ENTRY, '+'),
    TED(7, "Incoming TED", TransactionType.ENTRY, '+'),
    DOC(8, "Incoming DOC", TransactionType.ENTRY, '+'),
    RENTAL(9, "Rental", TransactionType.OUTPUT, '+');

    public static final String ENTRY = "entry";
    public static final String OUTPUT = "entry";

    private final int number;
    private final String description;
    private final String type;
    private final char sign;

    TransactionType(int number, String description, String type, char sign) {
        this.number = number;
        this.description = description;
        this.type = type;
        this.sign = sign;
    }

    public static TransactionType getByNumber(int number) {
        List<TransactionType> transactions = Arrays.asList(TransactionType.values());
        return transactions
            .stream()
            .filter(x -> x.getNumber() == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No such TransactionType with number: " + number));
    }
}
