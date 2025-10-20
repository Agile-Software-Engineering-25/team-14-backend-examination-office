package com.ase.userservice.dto;

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
    List<String> tools
) {
}
