package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotNull;

// Data received when rescheduling an appointment
public class RescheduleRequest {

    // New slot selected for the appointment
    @NotNull
    private Long newSlotId;

    public Long getNewSlotId() {
        return newSlotId;
    }

    public void setNewSlotId(Long newSlotId) {
        this.newSlotId = newSlotId;
    }
}