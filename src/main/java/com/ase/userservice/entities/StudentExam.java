package com.ase.userservice.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student_exams")
public class StudentExam {

  @EmbeddedId
  private StudentExamId id = new StudentExamId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("studentId")
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("examId")
  @JoinColumn(name = "exam_id", nullable = false)
  private Exam exam;

  @Enumerated(EnumType.STRING)
  private ExamState state = ExamState.EXAM_OPEN;
}
