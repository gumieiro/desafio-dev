package com.gumieiro.devchallenge.services.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralException extends RuntimeException {
    public GeneralException(Object error) {
        super("General Exception: " + error);
        log.info("Error: " + getMessage());
    }
}