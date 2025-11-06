package com.ase.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StudentDto {
  private String uuid;
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String matriculationNumber;
  private String degreeProgram;
  private int semester;
  private String studyStatus;
  private String cohort;
  private String address;
  private String phoneNumber;
  private String dateOfBirth;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean enlisted;
}
