package com.gumieiro.devchallenge.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Resource Not Found. Resource Id: " + id);
    }
}