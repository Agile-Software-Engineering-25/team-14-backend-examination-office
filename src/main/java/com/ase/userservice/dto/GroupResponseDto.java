package com.ase.userservice.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDto {
  @JsonProperty("group_count")
  private int groupCount;

  @JsonProperty("groups")
  private List<GroupDto> groups;
}
