package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

// This repository manages Doctor data in the database
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
