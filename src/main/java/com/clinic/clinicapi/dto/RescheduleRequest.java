package com.clinic.clinicapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Data received when rescheduling an appointment
public class RescheduleRequest {

    // New slot selected for the appointment
    @NotNull(message = "New slot id is required")
    @Positive(message = "New slot id must be greater than 0")
    private Long newSlotId;

    public Long getNewSlotId() {
        return newSlotId;
    }

    public void setNewSlotId(Long newSlotId) {
        this.newSlotId = newSlotId;
    }
}