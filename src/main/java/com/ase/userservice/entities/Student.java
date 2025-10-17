package com.ase.userservice.entities;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "students")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @ToString.Include
  private String id;

  @Column(nullable = false, unique = true, length = 20)
  @ToString.Include
  private String studentId;

  @Column(nullable = false, length = 100)
  @ToString.Include
  private String firstName;

  @Column(nullable = false, length = 100)
  @ToString.Include
  private String lastName;

  @Column(nullable = false, unique = true, length = 150)
  private String email;

  @Column(length = 100)
  private String studyGroup;

  @Column
  private Integer semester;

  @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
  private Set<Exam> exams = new HashSet<>();

  public Student(String studentId, String firstName, String lastName,
                 String email, String studyGroup, Integer semester) {
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.studyGroup = studyGroup;
    this.semester = semester;
  }

  public void addExam(Exam exam) {
    if (exam != null && exams.add(exam)) {
      exam.getStudents().add(this);
    }
  }

  public void removeExam(Exam exam) {
    if (exam != null && exams.remove(exam)) {
      exam.getStudents().remove(this);
    }
  }

  public Set<Exam> getExams() {
    return Set.copyOf(exams);
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public void updateFrom(Student other) {
    if (other.firstName != null) {
      this.firstName = other.firstName;
    }
    if (other.lastName != null) {
      this.lastName = other.lastName;
    }
    if (other.email != null) {
      this.email = other.email;
    }
    this.studyGroup = other.studyGroup;
    this.semester = other.semester;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student other = (Student) o;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
