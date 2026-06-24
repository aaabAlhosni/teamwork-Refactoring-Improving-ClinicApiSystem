package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

// Data received when creating a doctor
public class DoctorRequest {

    // Doctor name cannot be empty
    @NotBlank(message = "Doctor name is required")
    @Size(min = 2, max = 100, message = "Doctor name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Doctor name can contain letters and spaces only")
    private String name;

    // Specialty cannot be empty
    @NotBlank(message = "Specialty is required")
    @Size(min = 2, max = 100, message = "Specialty must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Specialty can contain letters and spaces only")
    private String specialty;

    // Working start time is required
    @NotNull(message = "Working start time is required")
    private LocalTime workingStart;

    // Working end time is required
    @NotNull(message = "Working end time is required")
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