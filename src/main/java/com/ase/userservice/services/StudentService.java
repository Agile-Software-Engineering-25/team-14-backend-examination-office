package com.ase.userservice.services;

import java.util.*;
import java.util.stream.Stream;
import com.ase.userservice.dto.ExternalStudentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ase.userservice.controllers.NotFoundException;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.Student;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentRepository;
import jakarta.persistence.EntityManager;

@Service
@Transactional
public class StudentService {

  private final StudentRepository studentRepository;
  private final ExamRepository examRepository;
  private final EntityManager em;
  private final ExternalStudentService externalStudentService;

  public StudentService(StudentRepository studentRepository,
                        ExamRepository examRepository,
                        EntityManager em,
                        ExternalStudentService externalStudentService) {
    this.studentRepository = studentRepository;
    this.examRepository = examRepository;
    this.em = em;
    this.externalStudentService = externalStudentService;
  }

  @Transactional(readOnly = true)
  public List<Student> getAllStudents() {
    List<Student> internal = studentRepository.findAll();

    List<ExternalStudentDto> externalDtos = externalStudentService.getAllExternalStudents();

    List<Student> external = externalDtos.stream()
        .map(e -> {
          Student s = new Student();
          try {
            s.setId(UUID.fromString(e.getUuid()));
          } catch (Exception ex) {
            s.setId(null);
          }
          s.setFirstName(e.getFirstName());
          s.setLastName(e.getLastName());
          s.setEmail(e.getEmail());
          s.setMatriculationId(e.getMatriculationNumber());
          s.setSemester(e.getSemester());
          return s;
        })
        .toList();

    return Stream.concat(internal.stream(), external.stream()).toList();
  }

  @Transactional(readOnly = true)
  public Student getStudentById(UUID id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Student with ID " + id + " not found"));
  }

  @Transactional(readOnly = true)
  public Student getStudentByStudentId(String studentId) {
    return studentRepository.findByMatriculationId(studentId)
        .orElseThrow(() -> new NotFoundException(
            "Student with matriculation number " + studentId + " not found"
        ));
  }

  public Student createStudent(Student student) {
    if (studentRepository.existsByMatriculationId(student.getMatriculationId())) {
      throw new IllegalArgumentException(
          "Student with matriculation number " + student.getMatriculationId() + " already exists"
      );
    }
    if (studentRepository.existsByEmail(student.getEmail())) {
      throw new IllegalArgumentException(
          "Student with email " + student.getEmail() + " already exists"
      );
    }
    return studentRepository.save(student);
  }

  public Student updateStudent(Student student) {
    if (student.getId() == null) {
      throw new IllegalArgumentException("Student ID cannot be null for update");
    }
    Student existing = getStudentById(student.getId());
    existing.updateFrom(student);
    return existing;
  }

  public void deleteStudent(String id) {
    Student student = getStudentById(UUID.fromString(id));
    studentRepository.delete(student);
  }

  @Transactional(readOnly = true)
  public List<Student> searchStudentsByName(String searchTerm) {
    return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        searchTerm,
        searchTerm
    );
  }

  @Transactional(readOnly = true)
  public List<Student> getStudentsByStudyGroup(String studyGroup) {
    return studentRepository.findByStudyGroup(studyGroup);
  }

  public void addStudentToExam(UUID studentId, UUID examId) {
      Student student = studentRepository.findById(studentId).orElseGet(() -> {
          ExternalStudentDto external = externalStudentService.getAllExternalStudents().stream()
              .filter(e -> e.getUuid() != null && e.getUuid().equals(studentId.toString()))
              .findFirst()
              .orElse(null);

          Student s = new Student();
          s.setId(studentId);
          if (external != null) {
              s.setFirstName(external.getFirstName());
              s.setLastName(external.getLastName());
              s.setEmail(external.getEmail());
              s.setMatriculationId(external.getMatriculationNumber());
              s.setSemester(external.getSemester());
          } else {
              s.setFirstName("Extern");
              s.setLastName(studentId.toString().substring(0, 8));
              s.setEmail(studentId + "@external.fake");
              s.setMatriculationId("ext-" + studentId.toString().substring(0, 8));
          }
        em.persist(s);
        em.flush();
        return s;
      });

      Exam exam = em.getReference(Exam.class, examId);
      student.addExam(exam);
    em.merge(student);
    em.flush();
  }

  @Transactional
  public void removeStudentFromExam(UUID studentId, UUID examId) {
    Student student = em.getReference(Student.class, studentId);
    Exam exam = em.getReference(Exam.class, examId);
    student.removeExam(exam);
    studentRepository.save(student);
  }

  @Transactional(readOnly = true)
  public List<Student> getStudentsByExamId(String examId) {
    return studentRepository.findStudentsByExamId(UUID.fromString(examId));
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> getExamsForStudent(String studentId) {
    Student student = studentRepository.findByIdWithExams(UUID.fromString(studentId))
        .orElseThrow(() -> new NotFoundException("Student not found"));

    return student.getExams().stream()
        .map(ExamService::toResponse)
        .toList();
  }

  public List<String> getAllExternalGroupNames() {
    return externalStudentService.getAllGroupNames();
  }
}
