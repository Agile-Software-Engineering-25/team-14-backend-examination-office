package com.ase.userservice.dto;

import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamState;

import java.time.LocalDateTime;
import java.util.List;

public record ExamResponse(
    String id,
    String title,
    String moduleCode,
    LocalDateTime examDate,
    String room,
    String examType,
    String semester,
    Integer ects,
    Integer maxPoints,
    Integer duration,
    Integer attemptNumber,
    boolean fileUploadRequired,
    ExamState examState,
    List<String> tools
) {
  public static ExamResponse from(Exam exam) {
    return new ExamResponse(
        exam.getId(),
        exam.getTitle(),
        exam.getModuleCode(),
        exam.getExamDate(),
        exam.getRoom(),
        exam.getExamType(),
        exam.getSemester(),
        exam.getEcts(),
        exam.getMaxPoints(),
        exam.getDuration(),
        exam.getAttemptNumber(),
        exam.isFileUploadRequired(),
        exam.getExamState(),
        exam.getTools()
    );
  }
}
