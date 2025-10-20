package com.ase.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequest {

  @NotBlank(message = "Student ID ist erforderlich")
  private String studentId;

  @NotBlank(message = "Vorname ist erforderlich")
  private String firstName;

  @NotBlank(message = "Nachname ist erforderlich")
  private String lastName;

  @NotBlank(message = "E-Mail ist erforderlich")
  @Email(message = "Gültige E-Mail-Adresse erforderlich")
  private String email;

  private String studyGroup;

  private Integer semester;
}
