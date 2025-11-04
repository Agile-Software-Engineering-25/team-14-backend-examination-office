package com.ase.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ExternalGroup {
  @JsonProperty("name")
  private String name;

  @JsonProperty("students_count")
  private int studentsCount;

  private List<ExternalStudentDto> students;
}
