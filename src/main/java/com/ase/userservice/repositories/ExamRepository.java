package com.ase.userservice.repositories;

import com.ase.userservice.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

  boolean existsByModuleCodeAndExamDateAndAttemptNumber(
      String moduleCode, LocalDate examDate, Integer attemptNumber);

  boolean existsByModuleCodeAndExamDateAndAttemptNumberAndIdNot(
      String moduleCode, LocalDate examDate, Integer attemptNumber, Long id);
}
