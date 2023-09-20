package com.gumieiro.devchallenge.services.exceptions;

public class GeneralException extends RuntimeException {
    public GeneralException(Object error) {
        super("General Exception: " + error);
    }
}