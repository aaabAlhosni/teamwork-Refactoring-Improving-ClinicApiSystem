package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Doctor;
import com.clinic.clinicapi.entity.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// This repository manages doctor data
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Search doctors by name
    Page<Doctor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Search doctors by specialty
    Page<Doctor> findBySpecialty(Specialty specialty, Pageable pageable);

    // Search doctors by name and specialty
    Page<Doctor> findByNameContainingIgnoreCaseAndSpecialty(
            String name,
            Specialty specialty,
            Pageable pageable
    );
}