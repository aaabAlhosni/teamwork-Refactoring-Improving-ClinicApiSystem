package com.clinic.clinicapi.service;

import com.clinic.clinicapi.dto.AppointmentRequest;
import com.clinic.clinicapi.dto.RescheduleRequest;
import com.clinic.clinicapi.entity.Appointment;
import com.clinic.clinicapi.entity.AppointmentSlot;
import com.clinic.clinicapi.entity.AppointmentStatus;
import com.clinic.clinicapi.entity.Patient;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.exception.ConflictException;
import com.clinic.clinicapi.exception.ResourceNotFoundException;
import com.clinic.clinicapi.repository.AppointmentRepository;
import com.clinic.clinicapi.repository.AppointmentSlotRepository;
import com.clinic.clinicapi.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Contains appointment business logic
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;
    private final PatientRepository patientRepository;

    // Constructor to receive repositories
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AppointmentSlotRepository appointmentSlotRepository,
            PatientRepository patientRepository) {

        this.appointmentRepository = appointmentRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
        this.patientRepository = patientRepository;
    }

    // Book a patient into an available slot
    @Transactional
    public Appointment bookAppointment(AppointmentRequest request) {

        // Find the patient
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found")
                );

        // Find the selected slot
        AppointmentSlot slot = appointmentSlotRepository.findById(request.getSlotId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment slot not found")
                );

        // The slot must be available
        if (!slot.isAvailable()) {
            throw new ConflictException("This appointment slot is already booked");
        }

        // A patient cannot have two booked appointments
        // with the same doctor on the same day
        boolean hasBookedAppointment =
                appointmentRepository
                        .existsByPatientIdAndSlotDoctorIdAndSlotSlotDateAndStatus(
                                patient.getId(),
                                slot.getDoctor().getId(),
                                slot.getSlotDate(),
                                AppointmentStatus.BOOKED
                        );

        if (hasBookedAppointment) {
            throw new ConflictException(
                    "Patient already has a booked appointment with this doctor on this day"
            );
        }

        // Create the appointment
        Appointment appointment = new Appointment(patient, slot);

        // Make the slot unavailable
        slot.setAvailable(false);
        appointmentSlotRepository.save(slot);

        return appointmentRepository.save(appointment);
    }

    // Cancel an appointment without deleting it
    @Transactional
    public Appointment cancelAppointment(Long appointmentId) {

        Appointment appointment = getAppointmentById(appointmentId);

        // Only a booked appointment can be cancelled
        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new ConflictException(
                    "Only a booked appointment can be cancelled"
            );
        }

        // Change appointment status
        appointment.setStatus(AppointmentStatus.CANCELLED);

        // Make its slot available again
        AppointmentSlot slot = appointment.getSlot();
        slot.setAvailable(true);

        appointmentSlotRepository.save(slot);

        return appointmentRepository.save(appointment);
    }

    // Reschedule an appointment to a new available slot
    @Transactional
    public Appointment rescheduleAppointment(
            Long appointmentId, RescheduleRequest request) {

        Appointment oldAppointment = getAppointmentById(appointmentId);

        // Only a booked appointment can be rescheduled
        if (oldAppointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new ConflictException(
                    "Only a booked appointment can be rescheduled"
            );
        }

        // The new slot cannot be the same as the old slot
        if (oldAppointment.getSlot().getId().equals(request.getNewSlotId())) {
            throw new BadRequestException(
                    "Please choose a different appointment slot"
            );
        }

        // Find the new slot
        AppointmentSlot newSlot = appointmentSlotRepository
                .findById(request.getNewSlotId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("New appointment slot not found")
                );

        // New slot must be available
        if (!newSlot.isAvailable()) {
            throw new ConflictException("New appointment slot is already booked");
        }

        // Check whether the new slot has the same doctor and date
        boolean sameDoctorAndDate =
                oldAppointment.getSlot().getDoctor().getId()
                        .equals(newSlot.getDoctor().getId())
                        && oldAppointment.getSlot().getSlotDate()
                        .equals(newSlot.getSlotDate());

        // Prevent another active appointment with the same doctor on the same day
        if (!sameDoctorAndDate) {
            boolean hasBookedAppointment =
                    appointmentRepository
                            .existsByPatientIdAndSlotDoctorIdAndSlotSlotDateAndStatus(
                                    oldAppointment.getPatient().getId(),
                                    newSlot.getDoctor().getId(),
                                    newSlot.getSlotDate(),
                                    AppointmentStatus.BOOKED
                            );

            if (hasBookedAppointment) {
                throw new ConflictException(
                        "Patient already has a booked appointment with this doctor on this day"
                );
            }
        }

        // Create a new appointment for the new slot
        Appointment newAppointment = new Appointment(
                oldAppointment.getPatient(),
                newSlot
        );

        // Mark new slot as unavailable
        newSlot.setAvailable(false);
        appointmentSlotRepository.save(newSlot);

        Appointment savedNewAppointment =
                appointmentRepository.save(newAppointment);

        // Keep the old appointment as history
        oldAppointment.setStatus(AppointmentStatus.RESCHEDULED);

        // Make the old slot available again
        AppointmentSlot oldSlot = oldAppointment.getSlot();
        oldSlot.setAvailable(true);
        appointmentSlotRepository.save(oldSlot);

        // Link old appointment to the new appointment
        oldAppointment.setRescheduledTo(savedNewAppointment);
        appointmentRepository.save(oldAppointment);

        return savedNewAppointment;
    }

    // Get one appointment by ID
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found")
                );
    }
}


