package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Data received when booking an appointment
public class AppointmentRequest {

    // Patient who wants to book
    @NotNull(message = "Patient id is required")
    @Positive(message = "Patient id must be greater than 0")
    private Long patientId;

    // Available slot selected by the patient
    @NotNull(message = "Slot id is required")
    @Positive(message = "Slot id must be greater than 0")
    private Long slotId;

    public Long getPatientId() {
        return patientId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }
}