package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// Data received when creating a patient
public class PatientRequest {

    // Patient name cannot be empty
    @NotBlank
    private String name;

    // Date of birth is required
    @NotNull
    private LocalDate dateOfBirth;

    // Phone number cannot be empty
    @NotBlank
    private String phone;

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


