package com.gumieiro.devchallenge.services.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
        log.info("Error: " + getMessage());
    }
}
