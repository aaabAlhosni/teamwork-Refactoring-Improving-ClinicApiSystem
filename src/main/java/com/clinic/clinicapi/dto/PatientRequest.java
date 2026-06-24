package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// Data received when creating a patient
public class PatientRequest {

    // Patient name cannot be empty
    @NotBlank(message = "Patient name is required")
    @Size(min = 2, max = 100, message = "Patient name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Patient name can contain letters and spaces only")
    private String name;

    // Date of birth is required
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    // Phone must be 8 digits and start with 9
    @NotBlank
    @Pattern(
            regexp = "^9\\d{7}$",
            message = "Phone number must be 8 digits and start with 9"
    )
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