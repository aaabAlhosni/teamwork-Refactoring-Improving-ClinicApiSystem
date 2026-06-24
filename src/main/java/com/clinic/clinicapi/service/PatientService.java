package com.clinic.clinicapi.service;

import com.clinic.clinicapi.dto.PatientRequest;
import com.clinic.clinicapi.entity.Patient;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.exception.ResourceNotFoundException;
import com.clinic.clinicapi.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// Contains patient business logic
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Constructor to receive the repository
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Create a new patient
    public Patient createPatient(PatientRequest request) {

        // Date of birth cannot be in the future
        if (request.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new BadRequestException(
                    "Date of birth cannot be in the future"
            );
        }

        Patient patient = new Patient(
                request.getName(),
                request.getDateOfBirth(),
                request.getPhone()
        );

        return patientRepository.save(patient);
    }

    // Get one patient by ID
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found")
                );
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}

