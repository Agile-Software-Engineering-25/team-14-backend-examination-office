package com.ase.userservice.controllers;

import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.dto.ExamDto;
import com.ase.userservice.services.dto.ExamFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
package com.ase.userservice.controllers;

import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.dto.ExamDto;
import com.ase.userservice.services.dto.ExamFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/v1/exams")
public class ExamController {

    private final ExamService service;

    public ExamController(ExamService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ExamDto>> search(
            @RequestParam Optional<String> query,
            @RequestParam Optional<String> moduleCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> examDateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> examDateTo,
            @RequestParam Optional<String> room,
            @RequestParam Optional<String> examType,
            @RequestParam Optional<String> semester,
            Pageable pageable
    ) {
        // Validierung des Datumsbereichs (TDD-Fall "INVALID_DATE_RANGE")
        if (examDateFrom.isPresent() && examDateTo.isPresent()
                && examDateFrom.get().isAfter(examDateTo.get())) {
            throw new InvalidDateRangeException();
        }

        // Query-Parameter in ein Filterobjekt mappen
        ExamFilter filter = new ExamFilter(
                query, moduleCode, examDateFrom, examDateTo, room, examType, semester
        );

        // Suche/Filter an Service delegieren und Paged-Ergebnis zurückgeben
        Page<ExamDto> result = service.search(filter, pageable);
        return ResponseEntity.ok(result);
    }
}
