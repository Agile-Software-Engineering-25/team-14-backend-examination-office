package com.ase.userservice.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.userservice.dto.CreateStudentRequest;
import com.ase.userservice.dto.StudentResponse;
import com.ase.userservice.entities.Student;
import com.ase.userservice.services.StudentService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public ResponseEntity<List<StudentResponse>> getAllStudents() {
    List<StudentResponse> response = studentService
        .getAllStudents()
        .stream()
        .map(StudentResponse::from)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> getStudentById(@PathVariable String id) {
    Student student = studentService.getStudentById(id);
    return ResponseEntity.ok(StudentResponse.from(student));
  }

  @GetMapping("/studentId/{studentId}")
  public ResponseEntity<StudentResponse> getStudentByStudentId(@PathVariable String studentId) {
    Student student = studentService.getStudentByStudentId(studentId);
    return ResponseEntity.ok(StudentResponse.from(student));
  }

  @PostMapping
  public ResponseEntity<StudentResponse> createStudent(
      @Valid @RequestBody CreateStudentRequest request
  ) {
    Student student = new Student(
        request.getStudentId(),
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getStudyGroup(),
        request.getSemester()
    );

    Student saved = studentService.createStudent(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(StudentResponse.from(saved));
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentResponse> updateStudent(
      @PathVariable String id,
      @Valid @RequestBody CreateStudentRequest request) {
    Student existing = studentService.getStudentById(id);
    existing.updateFrom(new Student(
        request.getStudentId(),
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getStudyGroup(),
        request.getSemester()
    ));
    Student updated = studentService.updateStudent(existing);
    return ResponseEntity.ok(StudentResponse.from(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
    studentService.deleteStudent(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<StudentResponse>> searchStudents(@RequestParam String q) {
    List<StudentResponse> response = studentService
        .searchStudentsByName(q)
        .stream()
        .map(StudentResponse::from)
        .toList();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/study-group/{studyGroup}")
  public ResponseEntity<List<StudentResponse>> getStudentsByStudyGroup(
      @PathVariable String studyGroup
  ) {
    List<StudentResponse> response = studentService
        .getStudentsByStudyGroup(studyGroup)
        .stream()
        .map(StudentResponse::from)
        .toList();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/{studentId}/exams/{examId}")
  public ResponseEntity<String> addStudentToExam(
      @PathVariable String studentId,
      @PathVariable String examId
  ) {
    studentService.addStudentToExam(studentId, examId);
    return ResponseEntity.ok("Student erfolgreich zur Prüfung hinzugefügt");
  }

  @DeleteMapping("/{studentId}/exams/{examId}")
  public ResponseEntity<String> removeStudentFromExam(
      @PathVariable String studentId,
      @PathVariable String examId
  ) {
    studentService.removeStudentFromExam(studentId, examId);
    return ResponseEntity.ok("Student erfolgreich von der Prüfung entfernt");
  }

  @GetMapping("/exam/{examId}")
  public ResponseEntity<List<StudentResponse>> getStudentsByExamId(@PathVariable String examId) {
    List<StudentResponse> response = studentService
        .getStudentsByExamId(examId)
        .stream()
        .map(StudentResponse::from)
        .toList();

    return ResponseEntity.ok(response);
  }
}
