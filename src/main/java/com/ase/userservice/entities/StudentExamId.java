package com.ase.userservice.entities;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExamId implements Serializable {
  private UUID studentId;
  private UUID examId;
}
