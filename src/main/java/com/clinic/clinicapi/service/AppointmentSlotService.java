package com.clinic.clinicapi.service;

import com.clinic.clinicapi.dto.GenerateSlotsRequest;
import com.clinic.clinicapi.entity.AppointmentSlot;
import com.clinic.clinicapi.entity.Doctor;
import com.clinic.clinicapi.exception.BadRequestException;
import com.clinic.clinicapi.exception.ConflictException;
import com.clinic.clinicapi.exception.ResourceNotFoundException;
import com.clinic.clinicapi.repository.AppointmentSlotRepository;
import com.clinic.clinicapi.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Contains appointment slot business logic
@Service
public class AppointmentSlotService {

    private final AppointmentSlotRepository appointmentSlotRepository;
    private final DoctorRepository doctorRepository;

    // Each slot is 30 minutes
    private static final int SLOT_MINUTES = 30;

    public AppointmentSlotService(
            AppointmentSlotRepository appointmentSlotRepository,
            DoctorRepository doctorRepository) {

        this.appointmentSlotRepository = appointmentSlotRepository;
        this.doctorRepository = doctorRepository;
    }

    // Generate 30-minute slots for one doctor on one date
    public List<AppointmentSlot> generateSlots(
            Long doctorId, GenerateSlotsRequest request) {

        // Find the doctor
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found")
                );

        LocalDate date = request.getDate();

        // Do not create slots in the past
        if (date.isBefore(LocalDate.now())) {
            throw new BadRequestException(
                    "Cannot generate slots for a past date"
            );
        }

        // Check if slots were already created for this doctor and date
        List<AppointmentSlot> existingSlots =
                appointmentSlotRepository
                        .findByDoctorIdAndSlotDate(doctorId, date);

        if (!existingSlots.isEmpty()) {
            throw new ConflictException(
                    "Slots already exist for this doctor on this date"
            );
        }

        List<AppointmentSlot> slots = new ArrayList<>();

        LocalTime startTime = doctor.getWorkingStart();
        LocalTime endTime = doctor.getWorkingEnd();

        // Create slots until the working day ends
        while (!startTime.plusMinutes(SLOT_MINUTES).isAfter(endTime)) {

            LocalTime slotEndTime = startTime.plusMinutes(SLOT_MINUTES);

            AppointmentSlot slot = new AppointmentSlot(
                    doctor,
                    date,
                    startTime,
                    slotEndTime
            );

            slots.add(slot);

            // Move to the next 30-minute slot
            startTime = slotEndTime;
        }

        return appointmentSlotRepository.saveAll(slots);
    }

    // Get all slots for one doctor on one date
    public List<AppointmentSlot> getDoctorSchedule(
            Long doctorId, LocalDate date) {

        // Check that the doctor exists
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found");
        }

        return appointmentSlotRepository
                .findByDoctorIdAndSlotDate(doctorId, date);
    }
}


