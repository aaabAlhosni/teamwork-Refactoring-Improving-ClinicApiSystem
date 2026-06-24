package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// This repository manages appointment slots
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    // Find all slots for one doctor on one date
    List<AppointmentSlot> findByDoctorIdAndSlotDate(Long doctorId, LocalDate slotDate);
}
