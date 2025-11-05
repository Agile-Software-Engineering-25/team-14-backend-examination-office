package com.ase.userservice.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamState;
import com.ase.userservice.entities.StudentExam;
import com.ase.userservice.entities.StudentExamId;

public interface StudentExamRepository extends JpaRepository<StudentExam, StudentExamId> {
  int countByExamAndState(Exam exam, ExamState state);

  List<StudentExam> findByIdExamId(String examId);

  List<StudentExam> findByIdStudentId(String studentId);
}
