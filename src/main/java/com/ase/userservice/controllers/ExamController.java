package com.ase.userservice.controllers;

import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.services.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
  private final ExamService service;

  public ExamController(ExamService service) {
    this.service = service;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('exam:write') or hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<ExamResponse> createExam(@Valid @RequestBody CreateExamRequest req,
                                                 UriComponentsBuilder uri) {
    ExamResponse created = service.create(req);
    return ResponseEntity
      .created(uri.path("/api/exams/{id}").buildAndExpand(created.id()).toUri())
      .body(created);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('exam:write') or hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<ExamResponse> updateExam(@PathVariable Long id,
                                                 @Valid @RequestBody CreateExamRequest req) {
    return ResponseEntity.ok(service.update(id, req));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('exam:read') or hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<ExamResponse> getExam(@PathVariable Long id) {
    return ResponseEntity.ok(service.get(id));
  }

  @GetMapping
  @PreAuthorize("hasAuthority('exam:read') or hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<ExamResponse>> listExams() {
    return ResponseEntity.ok(service.list());
  }
}
