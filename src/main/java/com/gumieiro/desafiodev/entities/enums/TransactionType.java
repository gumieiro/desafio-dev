package com.gumieiro.desafiodev.entities.enums;

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

    private static final String ENTRY = "entry";
    private static final String OUTPUT = "entry";

    private int number;
    private String description;
    private String type;
    private char sign;

    TransactionType(int number, String description, String type, char sign) {
        this.number = number;
        this.description = description;
        this.type = type;
        this.sign = sign;

    }
}
