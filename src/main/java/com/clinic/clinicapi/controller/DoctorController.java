package com.clinic.clinicapi.controller;

import com.clinic.clinicapi.dto.DoctorRequest;
import com.clinic.clinicapi.dto.GenerateSlotsRequest;
import com.clinic.clinicapi.entity.AppointmentSlot;
import com.clinic.clinicapi.entity.Doctor;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.service.AppointmentSlotService;
import com.clinic.clinicapi.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Handles doctor API requests
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentSlotService appointmentSlotService;

    public DoctorController(
            DoctorService doctorService,
            AppointmentSlotService appointmentSlotService) {

        this.doctorService = doctorService;
        this.appointmentSlotService = appointmentSlotService;
    }

    // Create a new doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(
            @Valid @RequestBody DoctorRequest request) {

        Doctor doctor = doctorService.createDoctor(request);

        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    // Get doctors page by page with optional filters
    @GetMapping
    public Page<Doctor> getAllDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Page cannot be negative and size must be at least 1
        if (page < 0 || size < 1) {
            throw new BadRequestException(
                    "Page must be 0 or more and size must be at least 1"
            );
        }

        Pageable pageable = PageRequest.of(page, size);

        return doctorService.getAllDoctors(name, specialty, pageable);
    }

    // Get one doctor by ID
    @GetMapping("/{doctorId}")
    public Doctor getDoctorById(
            @PathVariable("doctorId") Long doctorId) {

        return doctorService.getDoctorById(doctorId);
    }

    // Generate slots for a doctor on one date
    @PostMapping("/{doctorId}/slots")
    public ResponseEntity<List<AppointmentSlot>> generateSlots(
            @PathVariable("doctorId") Long doctorId,
            @Valid @RequestBody GenerateSlotsRequest request) {

        List<AppointmentSlot> slots =
                appointmentSlotService.generateSlots(doctorId, request);

        return new ResponseEntity<>(slots, HttpStatus.CREATED);
    }

    // View doctor schedule for one date
    @GetMapping("/{doctorId}/schedule")
    public List<AppointmentSlot> getDoctorSchedule(
            @PathVariable("doctorId") Long doctorId,
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return appointmentSlotService.getDoctorSchedule(doctorId, date);
    }
}