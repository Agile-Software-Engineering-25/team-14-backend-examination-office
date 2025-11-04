package com.ase.userservice.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupsResponseDto {

  @JsonProperty("group_count")

  private int groupCount;

  @JsonProperty("groups")
  private List<GroupDto> groups;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor

  public static class GroupDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("students_count")
    private int studentsCount;

    @JsonProperty("students")
    private List<GroupStudentDto> students;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GroupStudentDto {
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("matriculationNumber")
    private String matriculationNumber;

    @JsonProperty("email")
    private String email;
  }
}

