package com.clinic.clinicapi.exception;

// Used when input data is not valid
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

