package com.clinic.clinicapi.controller;

import com.clinic.clinicapi.dto.PatientRequest;
import com.clinic.clinicapi.dto.PatientResponseDto;
import com.clinic.clinicapi.dto.VisitResponseDto;
import com.clinic.clinicapi.entity.Patient;
import com.clinic.clinicapi.entity.Visit;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.service.PatientService;
import com.clinic.clinicapi.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(
            @Valid @RequestBody PatientRequest request) {

        PatientResponseDto patient =
                PatientResponseDto.from(
                        patientService.createPatient(request));

        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PatientResponseDto> getAllPatients() {

        return patientService.getAllPatients()
                .stream()
                .map(PatientResponseDto::from)
                .toList();
    // Get patients page by page
    @GetMapping
    public Page<Patient> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Page cannot be negative and size must be at least 1
        if (page < 0 || size < 1) {
            throw new BadRequestException(
                    "Page must be 0 or more and size must be at least 1"
            );
        }

        Pageable pageable = PageRequest.of(page, size);

        return patientService.getAllPatients(pageable);
    }

    @GetMapping("/{patientId}")
    public PatientResponseDto getPatientById(
            @PathVariable("patientId") Long patientId) {

        return PatientResponseDto.from(
                patientService.getPatientById(patientId));
    }

    @GetMapping("/{patientId}/history")
    public List<VisitResponseDto> getPatientHistory(
            @PathVariable("patientId") Long patientId) {

        return visitService.getPatientHistory(patientId)
                .stream()
                .map(VisitResponseDto::from)
                .toList();
    }
}