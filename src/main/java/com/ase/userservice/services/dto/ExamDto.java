package com.ase.userservice.services.dto;

import java.time.LocalDate;

public record ExamDto(
        Long id,
        String title,
        String moduleCode,
        LocalDate examDate,
        String room,
        String examType,
        String semester
) {}
