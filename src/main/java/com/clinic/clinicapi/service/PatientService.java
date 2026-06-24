package com.clinic.clinicapi.service;

import com.clinic.clinicapi.dto.PatientRequest;
import com.clinic.clinicapi.entity.Patient;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.exception.ResourceNotFoundException;
import com.clinic.clinicapi.repository.PatientRepository;
import com.clinic.clinicapi.repository.PatientSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    // Get all patients with optional name, phone, age range filters, and sorting
    public List<Patient> getAllPatients(String name, String phone, Integer ageMin, Integer ageMax,
                                       String sortBy, String sortDir) {
        LocalDate dobFrom = (ageMax != null) ? LocalDate.now().minusYears(ageMax) : null;
        LocalDate dobTo   = (ageMin != null) ? LocalDate.now().minusYears(ageMin) : null;

        String sortField = switch (sortBy == null ? "" : sortBy) {
            case "age"  -> "dateOfBirth";
            case "name" -> "name";
            default     -> "id";
        };

        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return patientRepository.findAll(
                PatientSpecification.build(name, phone, dobFrom, dobTo),
                Sort.by(direction, sortField)
        );
    }

    // Get patients page by page
    public Page<Patient> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }
}