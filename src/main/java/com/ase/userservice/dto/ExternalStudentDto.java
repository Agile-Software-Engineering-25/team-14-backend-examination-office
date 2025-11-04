package com.ase.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExternalStudentDto {
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
}
