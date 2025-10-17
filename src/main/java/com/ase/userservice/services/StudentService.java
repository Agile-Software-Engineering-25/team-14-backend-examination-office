package com.ase.userservice.services;

import com.ase.userservice.controllers.NotFoundException;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.Student;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

  private final StudentRepository studentRepository;
  private final ExamRepository examRepository;

  public StudentService(StudentRepository studentRepository, ExamRepository examRepository) {
    this.studentRepository = studentRepository;
    this.examRepository = examRepository;
  }

  @Transactional(readOnly = true)
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Student getStudentById(String id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Student with ID " + id + " not found"));
  }

  @Transactional(readOnly = true)
  public Student getStudentByStudentId(String studentId) {
    return studentRepository.findByStudentId(studentId)
        .orElseThrow(() -> new NotFoundException("Student with matriculation number " + studentId + " not found"));
  }

  public Student createStudent(Student student) {
    if (studentRepository.existsByStudentId(student.getStudentId())) {
      throw new IllegalArgumentException("Student with matriculation number " + student.getStudentId() + " already exists");
    }
    if (studentRepository.existsByEmail(student.getEmail())) {
      throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
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
    Student student = getStudentById(id);
    studentRepository.delete(student);
  }

  @Transactional(readOnly = true)
  public List<Student> searchStudentsByName(String searchTerm) {
    return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchTerm, searchTerm);
  }

  @Transactional(readOnly = true)
  public List<Student> getStudentsByStudyGroup(String studyGroup) {
    return studentRepository.findByStudyGroup(studyGroup);
  }

  public void addStudentToExam(String studentId, String examId) {
    Student student = getStudentById(studentId);
    Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new NotFoundException("Exam with ID " + examId + " not found"));

    student.addExam(exam);
    try {
      studentRepository.save(student);
    } catch (DataIntegrityViolationException ignored) {
      // safe to ignore duplicate relationship
    }
  }

  public void removeStudentFromExam(String studentId, String examId) {
    Student student = getStudentById(studentId);
    Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new NotFoundException("Exam with ID " + examId + " not found"));

    student.removeExam(exam);
    studentRepository.save(student);
  }

  @Transactional(readOnly = true)
  public List<Student> getStudentsByExamId(String examId) {
    return studentRepository.findStudentsByExamId(examId);
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> getExamsForStudent(String studentId) {
    Student student = studentRepository.findByStudentIdWithExams(studentId)
        .orElseThrow(() -> new NotFoundException("Student not found"));

    return student.getExams().stream()
        .map(ExamService::toResponse)
        .toList();
  }
}
