package com.ase.userservice.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
  private String name;

  @JsonProperty("students_count")
  private int studentCount;

  @JsonProperty("students")
  private List<StudentDto> students;
}
