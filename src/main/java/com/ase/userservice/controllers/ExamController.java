package com.ase.userservice.controllers;

import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.services.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    allowCredentials = "true",
    maxAge = 3600,
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS }
)
@RestController
@RequestMapping("/api/exams")
public class ExamController {
  private final ExamService service;

  public ExamController(ExamService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<ExamResponse> createExam(@Valid @RequestBody CreateExamRequest req,
                                                 UriComponentsBuilder uri) {
    ExamResponse created = service.create(req);
    return ResponseEntity
        .created(uri.path("/api/exams/{id}").buildAndExpand(created.id()).toUri())
        .body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExamResponse> updateExam(@PathVariable Long id,
                                                 @Valid @RequestBody CreateExamRequest req) {
    return ResponseEntity.ok(service.update(id, req));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExamResponse> getExam(@PathVariable Long id) {
    return ResponseEntity.ok(service.get(id));
  }

  @GetMapping
  public ResponseEntity<List<ExamResponse>> listExams() {
    return ResponseEntity.ok(service.list());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }
}
