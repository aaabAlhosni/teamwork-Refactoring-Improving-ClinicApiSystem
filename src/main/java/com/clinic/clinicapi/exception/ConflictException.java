package com.clinic.clinicapi.exception;

// Used when a business rule is broken
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}

