package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Appointment;
import com.clinic.clinicapi.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

// This repository manages appointments
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Check if the patient already has a booked appointment
    // with the same doctor on the same date
    boolean existsByPatientIdAndSlotDoctorIdAndSlotSlotDateAndStatus(
            Long patientId,
            Long doctorId,
            LocalDate slotDate,
            AppointmentStatus status
    );
}