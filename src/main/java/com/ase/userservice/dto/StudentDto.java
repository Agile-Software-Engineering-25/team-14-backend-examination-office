package com.ase.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StudentDto extends PersonDetailsDto {
  private String uuid;
  private String matriculationNumber;
  private String degreeProgram;
  private int semester;
  private String studyStatus;
  private String cohort;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean enlisted;
}
