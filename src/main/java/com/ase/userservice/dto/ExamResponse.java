package com.ase.userservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamType;

public record ExamResponse(
    String id,
    String title,
    String moduleCode,
    LocalDateTime examDate,
    String room,
    ExamType examType,
    String semester,
    Integer ects,
    Integer maxPoints,
    Integer duration,
    Integer attemptNumber,
    boolean fileUploadRequired,
    List<String> tools,
    Integer submissions
) {
  public ExamResponse(String id, String title, String moduleCode, LocalDateTime examDate,
                      String room, ExamType examType, String semester, Integer ects,
                      Integer maxPoints, Integer duration, Integer attemptNumber,
                      boolean fileUploadRequired, List<String> tools) {
    this(id, title, moduleCode, examDate, room, examType, semester, ects, maxPoints,
        duration, attemptNumber, fileUploadRequired, tools, 0);
  }

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
        exam.getTools()
    );
  }
}
