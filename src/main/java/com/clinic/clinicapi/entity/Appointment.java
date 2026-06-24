package com.clinic.clinicapi.entity;

import jakarta.persistence.*;

// This class represents booked appointments
@Entity
@Table(name = "appointments")
public class Appointment {

    // Primary key for each appointment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The patient who booked the appointment
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // The selected doctor time slot
    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private AppointmentSlot slot;

    // Current status of the appointment
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    // The new appointment after rescheduling
    @OneToOne
    @JoinColumn(name = "rescheduled_to_id")
    private Appointment rescheduledTo;

    // Empty constructor required by JPA
    public Appointment() {
    }

    // Constructor used to create an appointment
    public Appointment(Patient patient, AppointmentSlot slot) {
        this.patient = patient;
        this.slot = slot;
        this.status = AppointmentStatus.BOOKED;
    }

    // Getters return values
    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public AppointmentSlot getSlot() {
        return slot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Appointment getRescheduledTo() {
        return rescheduledTo;
    }

    // Setters update values
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setRescheduledTo(Appointment rescheduledTo) {
        this.rescheduledTo = rescheduledTo;
    }
}

