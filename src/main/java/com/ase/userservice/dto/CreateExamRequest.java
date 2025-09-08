package com.ase.userservice.dto;

import jakarta.validation.constraints.*;
import java.time.*;

public record CreateExamRequest(
  @NotBlank(message = "title is required") String title,
  @NotBlank(message = "moduleCode is required") String moduleCode,
  @NotNull LocalDate date,
  @NotNull LocalTime startTime,
  @NotNull LocalTime endTime,
  @NotBlank String examiner,
  String room,
  @NotNull @Positive Integer capacity,
  @PositiveOrZero Integer ects,
  @NotNull LocalDate registrationDeadline,
  @NotNull LocalDate deregistrationDeadline
) {
  public void validateBusinessRules() {
    if (endTime != null && startTime != null && !endTime.isAfter(startTime)) {
      throw new IllegalArgumentException("endTime must be after startTime");
    }
    if (registrationDeadline != null && date != null && registrationDeadline.isAfter(date)) {
      throw new IllegalArgumentException("registrationDeadline must be on/before date");
    }
    if (deregistrationDeadline != null && registrationDeadline != null && deregistrationDeadline.isBefore(registrationDeadline)) {
      throw new IllegalArgumentException("deregistrationDeadline must be on/after registrationDeadline");
    }
  }
}
