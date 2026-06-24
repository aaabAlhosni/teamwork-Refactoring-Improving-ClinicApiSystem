package com.clinic.clinicapi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

// This class represents the patients table in the database
@Entity
@Table(name = "patients")
public class Patient {

    // Primary key for each patient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient's full name
    private String name;

    // Patient's date of birth
    private LocalDate dateOfBirth;

    // Patient's phone number
    private String phone;

    // Empty constructor required by JPA
    public Patient() {
    }

    // Constructor used to create a patient object
    public Patient(String name, LocalDate dateOfBirth, String phone) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }

    // Getters return the values
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    // Setters update the values
    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

