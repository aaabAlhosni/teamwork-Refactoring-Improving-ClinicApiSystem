package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotNull;

// Data received when booking an appointment
public class AppointmentRequest {

    // Patient who wants to book
    @NotNull
    private Long patientId;

    // Available slot selected by the patient
    @NotNull
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
