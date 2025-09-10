package com.ase.userservice.dto;

import java.time.LocalDate;
import java.util.List;

public record ExamResponse(
  Long id,
  String title,
  String moduleCode,
  LocalDate examDate,
  String room,
  String examType,
  String semester,
  Integer ects,
  Integer maxPoints,
  Integer duration,
  Integer attemptNumber,
  boolean fileUploadRequired,
  List<String> tools
) {}
