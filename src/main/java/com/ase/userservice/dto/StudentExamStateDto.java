package com.ase.userservice.dto;

import com.ase.userservice.entities.ExamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentExamStateDto {
  private String studentUuid;
  private String examUuid;
  private ExamState state;
}
