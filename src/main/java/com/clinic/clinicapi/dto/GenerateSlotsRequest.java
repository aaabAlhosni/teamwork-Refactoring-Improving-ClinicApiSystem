package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// Data received when generating doctor slots
public class GenerateSlotsRequest {

    // The date for the new slots
    @NotNull(message = "Slot date is required")
    @FutureOrPresent(message = "Slot date cannot be in the past")
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}