package com.ase.userservice.repositories;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ase.userservice.entities.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

  boolean existsByModuleCodeAndExamDateAndAttemptNumber(
      String moduleCode,
      LocalDateTime examDate,
      Integer attemptNumber);

  boolean existsByModuleCodeAndExamDateAndAttemptNumberAndIdNot(
      String moduleCode,
      LocalDateTime examDate,
      Integer attemptNumber,
      Long id);
}
