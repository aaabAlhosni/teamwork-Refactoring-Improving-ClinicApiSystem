package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// This repository manages patient data
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Get patients page by page
    Page<Patient> findAll(Pageable pageable);
}

