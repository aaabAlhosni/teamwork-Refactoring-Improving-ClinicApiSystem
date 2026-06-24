package com.clinic.clinicapi.dto;

import com.clinic.clinicapi.entity.Patient;

import java.time.LocalDate;

public class PatientResponseDto {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;

    public PatientResponseDto() {
    }

    public PatientResponseDto(Long id,
                              String name,
                              LocalDate dateOfBirth,
                              String phone) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }

    public static PatientResponseDto from(Patient patient) {
        return new PatientResponseDto(
                patient.getId(),
                patient.getName(),
                patient.getDateOfBirth(),
                patient.getPhone()
        );
    }

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
}