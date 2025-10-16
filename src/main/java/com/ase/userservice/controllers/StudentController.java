package com.ase.userservice.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ase.userservice.dto.CreateStudentRequest;
import com.ase.userservice.dto.StudentResponse;
import com.ase.userservice.entities.Student;
import com.ase.userservice.services.CertificateService;
import com.ase.userservice.services.StudentService;

import jakarta.validation.Valid;

@CrossOrigin(
  origins = "http://localhost:5173",
  allowedHeaders = "*",
  allowCredentials = "true",
  maxAge = 3600,
  methods = { RequestMethod.GET,
      RequestMethod.POST,
      RequestMethod.PUT,
      RequestMethod.DELETE,
      RequestMethod.OPTIONS
  }
)
@RestController
@RequestMapping("/api/students")
public class StudentController {

  @Autowired
  private StudentService studentService;
  
  @Autowired
  private CertificateService certificateService;

  @GetMapping
  public ResponseEntity<List<StudentResponse>> getAllStudents() {
      List<Student> students = studentService.getAllStudents();
      List<StudentResponse> response = students.stream()
              .map(StudentResponse::new)
              .collect(Collectors.toList());
      return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
      Optional<Student> student = studentService.getStudentById(id);
      if (student.isPresent()) {
          return ResponseEntity.ok(new StudentResponse(student.get()));
      }
      throw new NotFoundException("Student mit ID " + id + " nicht gefunden");
  }

  @GetMapping("/studentId/{studentId}")
  public ResponseEntity<StudentResponse> getStudentByStudentId(@PathVariable String studentId) {
      Optional<Student> student = studentService.getStudentByStudentId(studentId);
      if (student.isPresent()) {
          return ResponseEntity.ok(new StudentResponse(student.get()));
      }
      throw new NotFoundException("Student mit Matrikelnummer " + studentId + " nicht gefunden");
  }

  @PostMapping
  public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
      try {
          Student student = new Student(
                  request.getStudentId(),
                  request.getFirstName(),
                  request.getLastName(),
                  request.getEmail(),
                  request.getStudyGroup(),
                  request.getSemester()
          );

          Student savedStudent = studentService.createStudent(student);
          return ResponseEntity.status(HttpStatus.CREATED)
                  .body(new StudentResponse(savedStudent));
      } catch (IllegalArgumentException e) {
          return ResponseEntity.badRequest().build();
      }
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id,
                                                     @Valid @RequestBody CreateStudentRequest request) {
      Optional<Student> existingStudentOpt = studentService.getStudentById(id);
      if (!existingStudentOpt.isPresent()) {
          throw new NotFoundException("Student mit ID " + id + " nicht gefunden");
      }

      Student existingStudent = existingStudentOpt.get();
      existingStudent.setStudentId(request.getStudentId());
      existingStudent.setFirstName(request.getFirstName());
      existingStudent.setLastName(request.getLastName());
      existingStudent.setEmail(request.getEmail());
      existingStudent.setStudyGroup(request.getStudyGroup());
      existingStudent.setSemester(request.getSemester());

      Student updatedStudent = studentService.updateStudent(existingStudent);
      return ResponseEntity.ok(new StudentResponse(updatedStudent));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
      try {
          studentService.deleteStudent(id);
          return ResponseEntity.noContent().build();
      } catch (IllegalArgumentException e) {
          throw new NotFoundException("Student mit ID " + id + " nicht gefunden");
      }
  }

  @GetMapping("/search")
  public ResponseEntity<List<StudentResponse>> searchStudents(@RequestParam String q) {
      List<Student> students = studentService.searchStudentsByName(q);
      List<StudentResponse> response = students.stream()
              .map(StudentResponse::new)
              .collect(Collectors.toList());
      return ResponseEntity.ok(response);
  }

  @GetMapping("/study-group/{studyGroup}")
  public ResponseEntity<List<StudentResponse>> getStudentsByStudyGroup(@PathVariable String studyGroup) {
      List<Student> students = studentService.getStudentsByStudyGroup(studyGroup);
      List<StudentResponse> response = students.stream()
              .map(StudentResponse::new)
              .collect(Collectors.toList());
      return ResponseEntity.ok(response);
  }

  @PostMapping("/{studentId}/exams/{examId}")
  public ResponseEntity<String> addStudentToExam(@PathVariable Long studentId, @PathVariable Long examId) {
      boolean success = studentService.addStudentToExam(studentId, examId);
      if (success) {
          return ResponseEntity.ok("Student erfolgreich zur Prüfung hinzugefügt");
      }
      return ResponseEntity.badRequest().body("Fehler beim Hinzufügen des Studenten zur Prüfung");
  }

  @DeleteMapping("/{studentId}/exams/{examId}")
  public ResponseEntity<String> removeStudentFromExam(@PathVariable Long studentId, @PathVariable Long examId) {
      boolean success = studentService.removeStudentFromExam(studentId, examId);
      if (success) {
          return ResponseEntity.ok("Student erfolgreich von der Prüfung entfernt");
      }
      return ResponseEntity.badRequest().body("Fehler beim Entfernen des Studenten von der Prüfung");
  }

  @GetMapping("/exam/{examId}")
  public ResponseEntity<List<StudentResponse>> getStudentsByExamId(@PathVariable Long examId) {
      List<Student> students = studentService.getStudentsByExamId(examId);
      List<StudentResponse> response = students.stream()
              .map(StudentResponse::new)
              .collect(Collectors.toList());
      return ResponseEntity.ok(response);
  }
  
  @GetMapping("/{id}/certificate")
  public ResponseEntity<byte[]> generateCertificate(@PathVariable Long id) {
      
      Optional<Student> studentOpt = studentService.getStudentById(id);
      if (!studentOpt.isPresent()) {
          throw new NotFoundException("Student mit ID " + id + " nicht gefunden");
      }
      
      Student student = studentOpt.get();

      String studyGroup = student.getStudyGroup();
      String degreeType = "Bachelor"; 
      
      if (studyGroup != null && !studyGroup.isEmpty()) {
          if (studyGroup.toUpperCase().startsWith("M")) {
              degreeType = "Master";
          }
      }
      
      try {
          byte[] pdfContent = certificateService.generateCertificate(student, degreeType);

          String filename = student.getLastName().toLowerCase() + "_" + 
                            degreeType.toLowerCase() + "_zeugnis.pdf";

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_PDF);
          headers.setContentDispositionFormData("attachment", filename);
          headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
          
          return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
          
      } catch (IOException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(("Fehler bei der Generierung des Zeugnisses: " + e.getMessage())
                  .getBytes());
      }
  }
}
