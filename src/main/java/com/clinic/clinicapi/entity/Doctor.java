package com.clinic.clinicapi.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

// This class represents the doctors table in the database
@Entity
@Table(name = "doctors")
public class Doctor {

    // Primary key for each doctor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Doctor's full name
    private String name;

    // Doctor's medical specialty from predefined values
    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    // Doctor's working start time
    private LocalTime workingStart;

    // Doctor's working end time
    private LocalTime workingEnd;

    // Empty constructor required by JPA
    public Doctor() {
    }

    // Constructor used to create a doctor object
    public Doctor(
            String name,
            Specialty specialty,
            LocalTime workingStart,
            LocalTime workingEnd) {

        this.name = name;
        this.specialty = specialty;
        this.workingStart = workingStart;
        this.workingEnd = workingEnd;
    }

    // Getters return the values
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public LocalTime getWorkingStart() {
        return workingStart;
    }

    public LocalTime getWorkingEnd() {
        return workingEnd;
    }

    // Setters update the values
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setWorkingStart(LocalTime workingStart) {
        this.workingStart = workingStart;
    }

    public void setWorkingEnd(LocalTime workingEnd) {
        this.workingEnd = workingEnd;
    }
}