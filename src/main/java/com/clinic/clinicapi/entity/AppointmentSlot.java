package com.clinic.clinicapi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

// This class represents appointment slots for doctors
@Entity
@Table(name = "appointment_slots")
public class AppointmentSlot {

    // Primary key for each slot
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The doctor who owns this slot
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Date of the appointment slot
    private LocalDate slotDate;

    // Slot start time
    private LocalTime startTime;

    // Slot end time
    private LocalTime endTime;

    // True means the slot can be booked
    private boolean available;

    // Empty constructor required by JPA
    public AppointmentSlot() {
    }

    // Constructor used to create a slot
    public AppointmentSlot(Doctor doctor, LocalDate slotDate,
                           LocalTime startTime, LocalTime endTime) {
        this.doctor = doctor;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = true;
    }

    // Getters return values
    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
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

    // Setters update values
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}


