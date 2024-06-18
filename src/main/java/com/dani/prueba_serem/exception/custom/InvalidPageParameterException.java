package com.dani.prueba_serem.exception.custom;

public class InvalidPageParameterException extends RuntimeException {

    public InvalidPageParameterException(String message) {
        super(message);
    }
}
