package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

// Data received when creating a doctor
public class DoctorRequest {

    // Doctor name cannot be empty
    @NotBlank
    private String name;

    // Specialty cannot be empty
    @NotBlank
    private String specialty;

    // Working start time is required
    @NotNull
    private LocalTime workingStart;

    // Working end time is required
    @NotNull
    private LocalTime workingEnd;

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalTime getWorkingStart() {
        return workingStart;
    }

    public LocalTime getWorkingEnd() {
        return workingEnd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setWorkingStart(LocalTime workingStart) {
        this.workingStart = workingStart;
    }

    public void setWorkingEnd(LocalTime workingEnd) {
        this.workingEnd = workingEnd;
    }
}

