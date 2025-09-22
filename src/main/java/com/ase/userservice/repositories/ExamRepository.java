package com.ase.userservice.repositories;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ase.userservice.entities.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

  boolean existsByModuleCodeAndExamDateAndAttemptNumber(
      String moduleCode, LocalDate examDate, Integer attemptNumber);

  boolean existsByModuleCodeAndExamDateAndAttemptNumberAndIdNot(
      String moduleCode, LocalDate examDate, Integer attemptNumber, Long id);
}
