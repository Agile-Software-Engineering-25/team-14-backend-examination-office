package com.ase.userservice.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentExamId implements Serializable {
    private String student;
    private String exam;
}