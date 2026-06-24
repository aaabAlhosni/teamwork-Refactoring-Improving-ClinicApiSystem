package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Data received when recording a patient visit
public class VisitRequest {

    // Doctor's diagnosis
    @NotBlank(message = "Diagnosis is required")
    @Size(min = 2, max = 500, message = "Diagnosis must be between 2 and 500 characters")
    private String diagnosis;

    // Doctor's prescription
    @NotBlank(message = "Prescription is required")
    @Size(min = 2, max = 500, message = "Prescription must be between 2 and 500 characters")
    private String prescription;

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}