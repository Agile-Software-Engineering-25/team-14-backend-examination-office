package com.ase.userservice.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.services.ExamService;
import jakarta.validation.Valid;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    allowCredentials = "true",
    maxAge = 3600,
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    }
)
@RestController
@RequestMapping("/api/exams")
public class ExamController {

  private final ExamService service;

  public ExamController(ExamService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<ExamResponse> createExam(
      @Valid @RequestBody CreateExamRequest req,
      UriComponentsBuilder uri) {
    ExamResponse created = service.create(req);
    return ResponseEntity
        .created(
            uri.path("/api/exams/{id}")
                .buildAndExpand(created.id())
                .toUri())
        .body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExamResponse> updateExam(
      @PathVariable Long id,
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
