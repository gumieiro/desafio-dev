package com.gumieiro.devchallenge.services.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Resource Not Found. Resource Id: " + id);
        log.info("Error: " + getMessage());
    }
}