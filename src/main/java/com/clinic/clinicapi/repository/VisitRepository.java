package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This repository manages visit records
public interface VisitRepository extends JpaRepository<Visit, Long> {

    // Find all visits for one patient
    List<Visit> findByAppointmentPatientId(Long patientId);

    // Check if an appointment already has a visit
    boolean existsByAppointmentId(Long appointmentId);
}

