package com.clinic.clinicapi.dto;

import com.clinic.clinicapi.entity.Appointment;

public class AppointmentResponseDto {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long slotId;
    private Long doctorId;
    private String doctorName;
    private String status;
    private Long rescheduledToId;

    public AppointmentResponseDto() {
    }

    public AppointmentResponseDto(Long id,
                                  Long patientId,
                                  String patientName,
                                  Long slotId,
                                  Long doctorId,
                                  String doctorName,
                                  String status,
                                  Long rescheduledToId) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.status = status;
        this.rescheduledToId = rescheduledToId;
    }

    public static AppointmentResponseDto from(Appointment appointment) {
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPatient().getName(),
                appointment.getSlot().getId(),
                appointment.getSlot().getDoctor().getId(),
                appointment.getSlot().getDoctor().getName(),
                appointment.getStatus().name(),
                appointment.getRescheduledTo() != null
                        ? appointment.getRescheduledTo().getId()
                        : null
        );
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getSlotId() {
        return slotId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getStatus() {
        return status;
    }

    public Long getRescheduledToId() {
        return rescheduledToId;
    }
}