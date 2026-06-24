package com.clinic.clinicapi.controller;

import com.clinic.clinicapi.dto.PatientRequest;
import com.clinic.clinicapi.dto.PatientResponseDto;
import com.clinic.clinicapi.dto.VisitResponseDto;
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


