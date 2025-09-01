package com.ase.userservice.services.dto;

import com.ase.userservice.entities.ExamType;
import java.time.LocalDate;
import java.util.Optional;

public record ExamFilter(
        Optional<String> query,
        Optional<String> moduleCode,
        Optional<LocalDate> examDateFrom,
        Optional<LocalDate> examDateTo,
        Optional<String> room,
        Optional<ExamType> examType,
        Optional<String> semester
) {}
