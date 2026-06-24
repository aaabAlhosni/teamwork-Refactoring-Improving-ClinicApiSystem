package com.clinic.clinicapi.controller;

import com.clinic.clinicapi.dto.AppointmentRequest;
import com.clinic.clinicapi.dto.AppointmentResponseDto;
import com.clinic.clinicapi.dto.RescheduleRequest;
import com.clinic.clinicapi.dto.VisitRequest;
import com.clinic.clinicapi.dto.VisitResponseDto;
import com.clinic.clinicapi.service.AppointmentService;
import com.clinic.clinicapi.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Handles appointment API requests
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final VisitService visitService;

    public AppointmentController(
            AppointmentService appointmentService,
            VisitService visitService) {

        this.appointmentService = appointmentService;
        this.visitService = visitService;
    }

    // Book an appointment
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        AppointmentResponseDto appointment =
                AppointmentResponseDto.from(
                        appointmentService.bookAppointment(request));

        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    // Cancel an appointment without deleting it
    @PostMapping("/{appointmentId}/cancel")
    public AppointmentResponseDto cancelAppointment(
            @PathVariable("appointmentId") Long appointmentId) {

        return AppointmentResponseDto.from(
                appointmentService.cancelAppointment(appointmentId));
    }

    // Reschedule an appointment to a new slot
    @PostMapping("/{appointmentId}/reschedule")
    public AppointmentResponseDto rescheduleAppointment(
            @PathVariable("appointmentId") Long appointmentId,
            @Valid @RequestBody RescheduleRequest request) {

        return AppointmentResponseDto.from(
                appointmentService
                        .rescheduleAppointment(appointmentId, request));
    }

    // Record diagnosis and prescription
    @PostMapping("/{appointmentId}/visit")
    public ResponseEntity<VisitResponseDto> recordVisit(
            @PathVariable("appointmentId") Long appointmentId,
            @Valid @RequestBody VisitRequest request) {

        VisitResponseDto visit =
                VisitResponseDto.from(
                        visitService.recordVisit(appointmentId, request));

        return new ResponseEntity<>(visit, HttpStatus.CREATED);
    }

    // Get one appointment by ID
    @GetMapping("/{appointmentId}")
    public AppointmentResponseDto getAppointmentById(
            @PathVariable("appointmentId") Long appointmentId) {

        return AppointmentResponseDto.from(
                appointmentService.getAppointmentById(appointmentId));
    }
}
