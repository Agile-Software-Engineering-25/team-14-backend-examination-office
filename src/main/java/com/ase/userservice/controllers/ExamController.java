package com.ase.userservice.controllers;

import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

  @GetMapping("/lecturer/{lecturerId}")
  public ResponseEntity<String> listLecturerExams(@PathVariable String lecturerId) {
    // TODO
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
        .body("Listing exams for lecturer is not implemented yet.");
  }
}
