package com.ase.userservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ase.userservice.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

  Optional<Student> findByMatriculationId(String matriculationId);

  @Query("""
      SELECT DISTINCT s
      FROM Student s
      LEFT JOIN FETCH s.studentExams se
      LEFT JOIN FETCH se.exam e
      WHERE s.id = :studentId
      """)
  Optional<Student> findByIdWithExams(
      @Param("studentId") UUID studentId
  );

  Optional<Student> findByEmail(String email);

  List<Student> findByStudyGroup(String studyGroup);

  List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
      String firstName, String lastName
  );

  @Query("""
      SELECT DISTINCT s
      FROM Student s
      JOIN s.studentExams se
      JOIN se.exam e
      WHERE e.id = :examId
      """)
  List<Student> findStudentsByExamId(@Param("examId") UUID examId);

  boolean existsByMatriculationId(String matriculationId);

  boolean existsByEmail(String email);
}
