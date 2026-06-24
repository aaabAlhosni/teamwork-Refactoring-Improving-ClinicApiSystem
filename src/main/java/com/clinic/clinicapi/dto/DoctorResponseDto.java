package com.clinic.clinicapi.dto;

import com.clinic.clinicapi.entity.Doctor;

import java.time.LocalTime;

public class DoctorResponseDto {

    private Long id;
    private String name;
    private String specialty;
    private LocalTime workingStart;
    private LocalTime workingEnd;

    public DoctorResponseDto() {
    }

    public DoctorResponseDto(Long id,
                             String name,
                             String specialty,
                             LocalTime workingStart,
                             LocalTime workingEnd) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.workingStart = workingStart;
        this.workingEnd = workingEnd;
    }

    public static DoctorResponseDto from(Doctor doctor) {
        return new DoctorResponseDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialty(),
                doctor.getWorkingStart(),
                doctor.getWorkingEnd()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalTime getWorkingStart() {
        return workingStart;
    }

    public LocalTime getWorkingEnd() {
        return workingEnd;
    }
}