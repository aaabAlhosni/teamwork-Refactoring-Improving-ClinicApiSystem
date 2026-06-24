package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// Data received when generating doctor slots
public class GenerateSlotsRequest {

    // The date for the new slots
    @NotNull
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}