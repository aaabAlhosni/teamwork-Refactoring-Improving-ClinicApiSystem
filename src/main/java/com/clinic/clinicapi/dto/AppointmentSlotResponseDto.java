package com.clinic.clinicapi.dto;

import com.clinic.clinicapi.entity.AppointmentSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentSlotResponseDto {

    private Long id;
    private Long doctorId;
    private String doctorName;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;

    public AppointmentSlotResponseDto() {
    }

    public AppointmentSlotResponseDto(Long id,
                                      Long doctorId,
                                      String doctorName,
                                      LocalDate slotDate,
                                      LocalTime startTime,
                                      LocalTime endTime,
                                      boolean available) {
        this.id = id;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    public static AppointmentSlotResponseDto from(AppointmentSlot slot) {
        return new AppointmentSlotResponseDto(
                slot.getId(),
                slot.getDoctor().getId(),
                slot.getDoctor().getName(),
                slot.getSlotDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.isAvailable()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }
}