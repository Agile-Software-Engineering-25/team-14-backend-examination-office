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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/v1/exams")
@Validated
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<Page<ExamDto>> search(
            @RequestParam Optional<String> query,
            @RequestParam Optional<String> moduleCode,
            @RequestParam Optional<@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate> examDateFrom,
            @RequestParam Optional<@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate> examDateTo,
            @RequestParam Optional<String> room,
            @RequestParam Optional<String> examType,
            @RequestParam Optional<String> semester,
            Pageable pageable
    ) {
        if (examDateFrom.isPresent() && examDateTo.isPresent()
                && examDateFrom.get().isAfter(examDateTo.get())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_DATE_RANGE");
        }

        ExamFilter filter = new ExamFilter(
                query, moduleCode, examDateFrom, examDateTo, room, examType, semester
        );
        Page<ExamDto> result = examService.search(filter, pageable);
        return ResponseEntity.ok(result);
    }
}
