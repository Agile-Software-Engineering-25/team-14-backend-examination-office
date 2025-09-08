package com.ase.userservice.dto;

import java.time.*;

public record ExamResponse(
  Long id,
  String title,
  String moduleCode,
  LocalDate date,
  LocalTime startTime,
  LocalTime endTime,
  String examiner,
  String room,
  Integer capacity,
  Integer ects,
  LocalDate registrationDeadline,
  LocalDate deregistrationDeadline
) {}
