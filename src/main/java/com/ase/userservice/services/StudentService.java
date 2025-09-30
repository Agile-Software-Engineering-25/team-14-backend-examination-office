package com.ase.userservice.services;

import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.Student;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public Student createStudent(Student student) {
        // Prüfen ob Matrikelnummer oder E-Mail bereits existiert
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new IllegalArgumentException("Student mit Matrikelnummer " + student.getStudentId() + " existiert bereits");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Student mit E-Mail " + student.getEmail() + " existiert bereits");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("Student ID darf nicht null sein für Update");
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student mit ID " + id + " existiert nicht");
        }
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudentsByName(String searchTerm) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            searchTerm, searchTerm);
    }

    public List<Student> getStudentsByStudyGroup(String studyGroup) {
        return studentRepository.findByStudyGroup(studyGroup);
    }

    /**
     * Fügt einen Studenten zu einer Prüfung hinzu
     */
    public boolean addStudentToExam(Long studentId, Long examId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Exam> examOpt = examRepository.findById(examId);

        if (studentOpt.isPresent() && examOpt.isPresent()) {
            Student student = studentOpt.get();
            Exam exam = examOpt.get();
            
            student.addExam(exam);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    /**
     * Entfernt einen Studenten von einer Prüfung
     */
    public boolean removeStudentFromExam(Long studentId, Long examId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Exam> examOpt = examRepository.findById(examId);

        if (studentOpt.isPresent() && examOpt.isPresent()) {
            Student student = studentOpt.get();
            Exam exam = examOpt.get();
            
            student.removeExam(exam);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    /**
     * Holt alle Studenten einer bestimmten Prüfung
     */
    public List<Student> getStudentsByExamId(Long examId) {
        return studentRepository.findStudentsByExamId(examId);
    }
}