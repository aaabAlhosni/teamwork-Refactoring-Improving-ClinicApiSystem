package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// This repository manages doctor data
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Get doctors page by page
    Page<Doctor> findAll(Pageable pageable);
}