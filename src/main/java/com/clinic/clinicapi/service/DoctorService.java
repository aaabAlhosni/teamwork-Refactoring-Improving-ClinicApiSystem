package com.clinic.clinicapi.service;

import com.clinic.clinicapi.dto.DoctorRequest;
import com.clinic.clinicapi.entity.Doctor;
import com.clinic.clinicapi.entity.Specialty;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.exception.ResourceNotFoundException;
import com.clinic.clinicapi.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Contains doctor business logic
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    // Constructor to receive the repository
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Create a new doctor
    public Doctor createDoctor(DoctorRequest request) {

        // Start time must be before end time
        if (!request.getWorkingStart().isBefore(request.getWorkingEnd())) {
            throw new BadRequestException(
                    "Working start time must be before working end time"
            );
        }

        Doctor doctor = new Doctor(
                request.getName(),
                request.getSpecialty(),
                request.getWorkingStart(),
                request.getWorkingEnd()
        );

        return doctorRepository.save(doctor);
    }

    // Get one doctor by ID
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found")
                );
    }

    // Get doctors page by page with optional search filters
    public Page<Doctor> getAllDoctors(
            String name,
            Specialty specialty,
            Pageable pageable) {

        boolean hasName = name != null && !name.trim().isEmpty();
        boolean hasSpecialty = specialty != null;

        if (hasName && hasSpecialty) {
            return doctorRepository
                    .findByNameContainingIgnoreCaseAndSpecialty(
                            name.trim(),
                            specialty,
                            pageable
                    );
        }

        if (hasName) {
            return doctorRepository
                    .findByNameContainingIgnoreCase(
                            name.trim(),
                            pageable
                    );
        }

        if (hasSpecialty) {
            return doctorRepository
                    .findBySpecialty(
                            specialty,
                            pageable
                    );
        }

        return doctorRepository.findAll(pageable);
    }
}