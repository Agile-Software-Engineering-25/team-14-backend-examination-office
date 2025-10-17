package com.ase.userservice.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ase.userservice.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

  Optional<Student> findByStudentId(String studentId);

  @Query("SELECT s FROM Student s LEFT JOIN FETCH s.exams WHERE s.studentId = :studentId")
  Optional<Student> findByStudentIdWithExams(@Param("studentId") String studentId);

  Optional<Student> findByEmail(String email);

  List<Student> findByStudyGroup(String studyGroup);

  List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
      String firstName, String lastName
  );

  @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.exams e WHERE e.id = :examId")
  List<Student> findStudentsByExamId(@Param("examId") String examId);

  boolean existsByStudentId(String studentId);

  boolean existsByEmail(String email);
}
