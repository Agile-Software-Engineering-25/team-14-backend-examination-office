package com.ase.userservice.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

public record CreateExamRequest(
  @NotBlank String title,
  @NotBlank String moduleCode,
  @NotNull LocalDate examDate,
  @NotBlank String room,
  @NotBlank String examType,
  @NotBlank String semester,
  @NotNull @PositiveOrZero Integer ects,
  @NotNull @Positive Integer maxPoints,
  @NotNull @Positive Integer duration,       // Minuten
  @NotNull @Positive Integer attemptNumber,  // 1..n
  boolean fileUploadRequired,
  @NotNull @Size(max = 20) List<@NotBlank String> tools
) {
  public void validateBusinessRules() {
    if (tools != null && tools.stream().anyMatch(s -> s == null || s.isBlank())) {
      throw new IllegalArgumentException("tools must not contain blank items");
    }
  }
}
