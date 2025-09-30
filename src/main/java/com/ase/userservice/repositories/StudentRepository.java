package com.ase.userservice.repositories;

import com.ase.userservice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

    List<Student> findByStudyGroup(String studyGroup);

    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName);

    @Query("SELECT s FROM Student s JOIN s.exams e WHERE e.id = :examId")
    List<Student> findStudentsByExamId(@Param("examId") Long examId);

    boolean existsByStudentId(String studentId);

    boolean existsByEmail(String email);
}
