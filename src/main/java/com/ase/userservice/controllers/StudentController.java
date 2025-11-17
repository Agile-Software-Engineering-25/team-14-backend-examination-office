package com.ase.userservice.controllers;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.userservice.dto.GroupDto;
import com.ase.userservice.dto.StudentDto;
import com.ase.userservice.services.CertificateService;
import com.ase.userservice.services.StudentService;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    allowCredentials = "true",
    maxAge = 3600,
    methods = {RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    }
)

@RestController
@RequestMapping("/api/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Autowired
  private CertificateService certificateService;

  @GetMapping("/study-group/{studyGroup}")
  public ResponseEntity<List<StudentDto>> getStudentsByStudyGroup(
      @PathVariable String studyGroup
  ) {
    return ResponseEntity.ok(studentService.getStudentsByStudyGroup(studyGroup));
  }

  @PostMapping("/{studentId}/exams/{examId}")
  public ResponseEntity<String> addStudentToExam(
      @PathVariable String studentId,
      @PathVariable String examId
  ) {
    studentService.addStudentToExam(studentId, examId);
    return ResponseEntity.ok("Student erfolgreich zur Prüfung hinzugefügt");
  }

  @GetMapping("/groups")
  public ResponseEntity<List<GroupDto>> getStudentGroups(
      @RequestParam(value = "examUuid", required = false) String examUuid) {

    List<GroupDto> groups;
    if (examUuid != null) {
      groups = studentService.getStudyGroupsForExam(examUuid);
    }
    else {
      groups = studentService.getStudyGroups();
    }

    return ResponseEntity.ok(groups);
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
  public ResponseEntity<List<String>> getStudentsByExamId(@PathVariable String examId) {
    List<String> response = studentService
        .getStudentIdsByExamId(examId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}/certificate")
  public ResponseEntity<byte[]> generateCertificate(@PathVariable String id) {
    StudentDto student = (StudentDto)studentService.getUserInfo(id);
    if (student == null) {
      throw new NotFoundException("Student mit ID " + id + " nicht gefunden");
    }

    String studyGroup = student.getCohort();
    String degreeType = "Bachelor";

    if (studyGroup != null && !studyGroup.isEmpty()) {
      if (studyGroup.toUpperCase().startsWith("M")) {
        degreeType = "Master";
      }
    }

    try {
      byte[] pdfContent = certificateService.generateCertificate(student, degreeType);

      String filename = student.getLastName().toLowerCase() + "_"
          + degreeType.toLowerCase() + "_zeugnis.pdf";

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_PDF);
      headers.setContentDispositionFormData("attachment", filename);
      headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

      return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }
    catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(("Fehler bei der Generierung des Zeugnisses: " + e.getMessage())
              .getBytes());
    }
  }

  @GetMapping("/{studyGroup}/certificates")
  public ResponseEntity<byte[]> generateCertificatesForStudyGroup(
      @PathVariable String studyGroup) {

    List<StudentDto> students = studentService.getStudentsByStudyGroup(studyGroup);
    if (students.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    try {
      byte[] pdfContent = certificateService.generateCertificates(students);

      String filename = studyGroup.toLowerCase() + "_zeugnisse.zip";

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.setContentDispositionFormData("attachment", filename);
      headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

      return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }
    catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(("Fehler bei der Generierung der Zeugnisse: " + e.getMessage())
              .getBytes());
    }
  }
}
