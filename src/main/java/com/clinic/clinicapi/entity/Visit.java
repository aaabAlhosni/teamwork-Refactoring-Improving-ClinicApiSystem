package com.clinic.clinicapi.entity;

import jakarta.persistence.*;

// This class represents a patient visit record
@Entity
@Table(name = "visits")
public class Visit {

    // Primary key for each visit
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The appointment linked to this visit
    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // Doctor's diagnosis for the patient
    private String diagnosis;

    // Doctor's prescription for the patient
    private String prescription;

    // Empty constructor required by JPA
    public Visit() {
    }

    // Constructor used to create a visit record
    public Visit(Appointment appointment, String diagnosis, String prescription) {
        this.appointment = appointment;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    // Getters return values
    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    // Setters update values
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}