package com.clinic.clinicapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Handles errors for the whole application
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Returns 404 when data is not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(
            ResourceNotFoundException exception) {

        return createResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    // Returns 409 when a business rule is broken
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleConflict(
            ConflictException exception) {

        return createResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    // Returns 400 when input data is not valid
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(
            BadRequestException exception) {

        return createResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    // Creates a simple JSON error response
    private ResponseEntity<Map<String, String>> createResponse(
            HttpStatus status, String message) {

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }
}

