package com.clinic.clinicapi.exception;

// Used when requested data does not exist
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

