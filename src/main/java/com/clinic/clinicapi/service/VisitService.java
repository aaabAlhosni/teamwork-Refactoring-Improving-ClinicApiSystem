package com.clinic.clinicapi.service;

import com.clinic.clinicapi.dto.VisitRequest;
import com.clinic.clinicapi.entity.Appointment;
import com.clinic.clinicapi.entity.AppointmentStatus;
import com.clinic.clinicapi.entity.Visit;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.exception.ConflictException;
import com.clinic.clinicapi.exception.ResourceNotFoundException;
import com.clinic.clinicapi.repository.AppointmentRepository;
import com.clinic.clinicapi.repository.PatientRepository;
import com.clinic.clinicapi.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// Contains visit business logic
@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    // Constructor to receive repositories
    public VisitService(
            VisitRepository visitRepository,
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository) {

        this.visitRepository = visitRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    // Record diagnosis and prescription after an appointment
    @Transactional
    public Visit recordVisit(Long appointmentId, VisitRequest request) {

        // Find the appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found")
                );

        // Only a booked appointment can have a visit
        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new ConflictException(
                    "Visit can only be recorded for a booked appointment"
            );
        }

        // Appointment time must be finished
        LocalDateTime appointmentEndTime = LocalDateTime.of(
                appointment.getSlot().getSlotDate(),
                appointment.getSlot().getEndTime()
        );

        if (appointmentEndTime.isAfter(LocalDateTime.now())) {
            throw new BadRequestException(
                    "Cannot record a visit before the appointment time ends"
            );
        }

        // One appointment can only have one visit
        if (visitRepository.existsByAppointmentId(appointmentId)) {
            throw new ConflictException(
                    "A visit already exists for this appointment"
            );
        }

        // Create visit record
        Visit visit = new Visit(
                appointment,
                request.getDiagnosis(),
                request.getPrescription()
        );

        // Mark appointment as completed
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return visitRepository.save(visit);
    }

    // Get full visit history for one patient
    public List<Visit> getPatientHistory(Long patientId) {

        // Check that the patient exists
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found");
        }

        return visitRepository.findByAppointmentPatientId(patientId);
    }
}
