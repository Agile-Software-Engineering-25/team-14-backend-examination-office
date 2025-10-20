package com.ase.userservice.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.Student;

public record StudentResponse(
    String id,
    String matriculationId,
    String firstName,
    String lastName,
    String email,
    String studyGroup,
    Integer semester,
    String fullName,
    List<String> examIds
) {
  public static StudentResponse from(Student student) {
    return new StudentResponse(
        student.getId(),
        student.getMatriculationId(),
        student.getFirstName(),
        student.getLastName(),
        student.getEmail(),
        student.getStudyGroup(),
        student.getSemester(),
        student.getFullName(),
        student.getExams().stream()
            .map(Exam::getId)
            .collect(Collectors.toList())
    );
  }
}
