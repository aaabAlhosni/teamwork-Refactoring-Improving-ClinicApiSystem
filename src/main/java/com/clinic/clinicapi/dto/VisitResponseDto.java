package com.clinic.clinicapi.dto;

import com.clinic.clinicapi.entity.Visit;

public class VisitResponseDto {

    private Long id;
    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private String diagnosis;
    private String prescription;

    public VisitResponseDto() {
    }

    public VisitResponseDto(Long id,
                            Long appointmentId,
                            Long patientId,
                            String patientName,
                            String diagnosis,
                            String prescription) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    public static VisitResponseDto from(Visit visit) {
        return new VisitResponseDto(
                visit.getId(),
                visit.getAppointment().getId(),
                visit.getAppointment().getPatient().getId(),
                visit.getAppointment().getPatient().getName(),
                visit.getDiagnosis(),
                visit.getPrescription()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }
}