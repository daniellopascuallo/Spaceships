package com.dani.prueba_serem.exception.global;

import com.dani.prueba_serem.exception.custom.InvalidPageParameterException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URISyntaxException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String REQUEST = ", Request: ";

    // handling entity not found exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Resource not found: " + exception.getMessage() + REQUEST + request.getDescription(false));
    }

    // handling invalid pagination parameters through custom exceptions
    @ExceptionHandler(InvalidPageParameterException.class)
    public ResponseEntity<String> handleInvalidPageParameterException(
            InvalidPageParameterException exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error: " + exception.getMessage() + REQUEST + request.getDescription(false));
    }

    // handling illegal argument exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error: " + exception.getMessage() + REQUEST + request.getDescription(false));
    }

    // handling URI syntax exception
    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<String> handleURISyntaxException(URISyntaxException exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid URI Syntax: " + exception.getMessage());
    }

    // handling other global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal error, Request: " + request.getDescription(false));
    }
}
