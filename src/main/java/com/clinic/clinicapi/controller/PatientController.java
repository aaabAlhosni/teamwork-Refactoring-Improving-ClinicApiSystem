package com.clinic.clinicapi.controller;

import com.clinic.clinicapi.dto.PatientRequest;
import com.clinic.clinicapi.entity.Patient;
import com.clinic.clinicapi.entity.Visit;
import com.clinic.clinicapi.service.PatientService;
import com.clinic.clinicapi.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Handles patient API requests
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final VisitService visitService;

    public PatientController(
            PatientService patientService,
            VisitService visitService) {

        this.patientService = patientService;
        this.visitService = visitService;
    }

    // Create a new patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @Valid @RequestBody PatientRequest request) {

        Patient patient = patientService.createPatient(request);

        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    // Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Get one patient by ID
    @GetMapping("/{patientId}")
    public Patient getPatientById(
            @PathVariable("patientId") Long patientId) {

        return patientService.getPatientById(patientId);
    }

    // Get full visit history for one patient
    @GetMapping("/{patientId}/history")
    public List<Visit> getPatientHistory(
            @PathVariable("patientId") Long patientId) {

        return visitService.getPatientHistory(patientId);
    }
}


