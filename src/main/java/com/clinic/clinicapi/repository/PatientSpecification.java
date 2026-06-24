package com.clinic.clinicapi.repository;

import com.clinic.clinicapi.entity.Patient;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {

    public static Specification<Patient> build(String name, String phone, LocalDate dobFrom, LocalDate dobTo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (phone != null && !phone.isBlank()) {
                predicates.add(cb.equal(root.get("phone"), phone));
            }

            if (dobFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateOfBirth"), dobFrom));
            }

            if (dobTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateOfBirth"), dobTo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
