package com.ase.userservice.controllers;

import com.ase.userservice.entities.ExamType;
import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.dto.ExamDto;
import com.ase.userservice.services.dto.ExamFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;
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
            @PageableDefault(size = 20, sort = "examDate", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (examDateFrom.isPresent() && examDateTo.isPresent()
                && examDateFrom.get().isAfter(examDateTo.get())) {
            throw new InvalidDateRangeException();
        }

        ExamType parsedExamType = parseExamTypeOrThrow(examType);
        ExamFilter filter = new ExamFilter(
                query, moduleCode, examDateFrom, examDateTo, room,
                Optional.ofNullable(parsedExamType), semester
        );

        return ResponseEntity.ok(service.search(filter, pageable));
    }

    private ExamType parseExamTypeOrThrow(Optional<String> raw) {
        if (raw.isEmpty()) return null;
        try {
            return ExamType.valueOf(raw.get().toUpperCase().trim());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException("EXAM_TYPE", raw.get());
        }
    }
}
