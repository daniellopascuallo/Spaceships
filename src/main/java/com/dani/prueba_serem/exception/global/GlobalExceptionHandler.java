package com.dani.prueba_serem.exception.global;

import com.dani.prueba_serem.exception.custom.InvalidPageParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handling invalid pagination parameters through custom exceptions
    @ExceptionHandler(InvalidPageParameterException.class)
    public ResponseEntity<String> handleInvalidPageParameterException(
            InvalidPageParameterException exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error: " + exception.getMessage() + ", Request: " + request.getDescription(false));
    }

    // handling illegal argument exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error: " + exception.getMessage() + ", Request: " + request.getDescription(false));
    }

    // handling other global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception exception, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal error, Request: " + request.getDescription(false));
    }
}
