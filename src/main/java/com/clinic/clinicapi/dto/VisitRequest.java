package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;

// Data received when recording a patient visit
public class VisitRequest {

    // Doctor's diagnosis
    @NotBlank
    private String diagnosis;

    // Doctor's prescription
    @NotBlank
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
