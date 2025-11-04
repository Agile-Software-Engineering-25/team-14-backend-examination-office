package com.ase.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ExternalGroupResponse {
  @JsonProperty("group_count")
  private int groupCount;
  private List<ExternalGroup> groups;
}

