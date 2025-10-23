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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.StudentService;
import jakarta.validation.Valid;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    allowCredentials = "true",
    maxAge = 3600
)
@RestController
@RequestMapping("/api/exams")
public class ExamController {

  private final ExamService examService;
  private final StudentService studentService;

  public ExamController(ExamService examService, StudentService studentService) {
    this.examService = examService;
    this.studentService = studentService;
  }

  @PostMapping
  public ResponseEntity<ExamResponse> createExam(@Valid @RequestBody CreateExamRequest req,
                                                 UriComponentsBuilder uri) {
    ExamResponse created = examService.create(req);
    return ResponseEntity
        .created(uri.path("/api/exams/{id}")
            .buildAndExpand(created.id())
            .toUri())
        .body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExamResponse> updateExam(@PathVariable String id,
                                                 @Valid @RequestBody CreateExamRequest req) {
    return ResponseEntity.ok(examService.update(id, req));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExamResponse> getExam(@PathVariable String id) {
    return ResponseEntity.ok(examService.get(id));
  }

  @GetMapping
  public ResponseEntity<List<ExamResponse>> listExams() {
    return ResponseEntity.ok(examService.list());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExam(@PathVariable String id) {
    examService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/student/{studentId}")
  public ResponseEntity<List<ExamResponse>> listStudentExams(@PathVariable String studentId) {
    return ResponseEntity.ok(studentService.getExamsForStudent(studentId));
  }

  @GetMapping("/module/{moduleCode}")
  public ResponseEntity<List<ExamResponse>> listModuleExams(@PathVariable String moduleCode) {
    return ResponseEntity.ok(examService.listByModuleCode(moduleCode));
  }
}
