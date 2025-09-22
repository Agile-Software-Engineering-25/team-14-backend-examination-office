package com.ase.userservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateExamRequest(
    @NotBlank String title,
    @NotBlank String moduleCode,
    @NotNull LocalDateTime examDate,
    @NotBlank String room,
    @NotBlank String examType,
    @NotBlank String semester,
    @NotNull @PositiveOrZero Integer ects,
    @NotNull @Positive Integer maxPoints,
    @NotNull @Positive Integer duration,
    @NotNull @Positive Integer attemptNumber,
    boolean fileUploadRequired,
    @NotNull @Size(max = 20) List<@NotBlank String> tools) {

  public void validateBusinessRules() {
    boolean hasBlank =
        tools != null
        && tools.stream().anyMatch(s -> s == null || s.isBlank());
    if (hasBlank) {
      throw new IllegalArgumentException(
          "tools must not contain blank items");
    }
  }
}
