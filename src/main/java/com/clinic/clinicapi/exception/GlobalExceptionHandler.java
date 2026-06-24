package com.clinic.clinicapi.exception;

import com.clinic.clinicapi.entity.Specialty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
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

    // Returns 400 when request validation fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Returns 400 when enum value in request body is invalid
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestBody(
            HttpMessageNotReadableException exception) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Invalid request value");
        response.put(
                "error",
                "Specialty must be one of: " + Arrays.toString(Specialty.values())
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Returns 400 when enum value in query parameter is invalid
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidQueryParameter(
            MethodArgumentTypeMismatchException exception) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Invalid query parameter");

        if ("specialty".equals(exception.getName())) {
            response.put(
                    "error",
                    "Specialty must be one of: " + Arrays.toString(Specialty.values())
            );
        } else {
            response.put(
                    "error",
                    exception.getName() + " has an invalid value"
            );
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Creates a simple JSON error response
    private ResponseEntity<Map<String, String>> createResponse(
            HttpStatus status, String message) {

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }
}