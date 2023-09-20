package com.gumieiro.devchallenge.services.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceException extends RuntimeException {

    public ServiceException(Throwable cause) {
        super(cause);
        log.info("Error: " + getMessage());
    }
}
