package com.ase.userservice.dto;

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
}
