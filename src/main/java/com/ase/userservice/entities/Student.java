package com.ase.userservice.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  private String matriculationId;

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

  @Column
  private LocalDate dateOfBirth;

  @OneToMany(
      mappedBy = "student",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<StudentExam> studentExams = new HashSet<>();

  public Student(String matriculationId, String firstName, String lastName,
                 String email, String studyGroup, Integer semester) {
    this.matriculationId = matriculationId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.studyGroup = studyGroup;
    this.semester = semester;
  }

  public Student(String matriculationId, String firstName, String lastName,
                 String email, String studyGroup, Integer semester, LocalDate dateOfBirth) {
    this.matriculationId = matriculationId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.studyGroup = studyGroup;
    this.semester = semester;
    this.dateOfBirth = dateOfBirth;
  }

  public void addExam(Exam exam) {
    if (exam == null) {
      return;
    }
    if (getStudentExam(exam) != null) {
      return;
    }

    StudentExam studentExam = new StudentExam();
    studentExam.setStudent(this);
    studentExam.setExam(exam);
    // explizit den Schlüssel setzen
    studentExam.setId(new StudentExamId(this.getId(), exam.getId()));

    this.getStudentExams().add(studentExam);
    exam.getStudentExams().add(studentExam);
  }

  public void removeExam(Exam exam) {
    if (exam != null) {
      studentExams.removeIf(studentExam -> {
        if (studentExam.getExam().equals(exam)) {
          exam.getStudentExams().remove(studentExam);
          return true;
        }
        return false;
      });
    }
  }

  public Set<Exam> getExams() {
    return studentExams.stream()
        .map(StudentExam::getExam)
        .collect(java.util.stream.Collectors.toSet());
  }

  public StudentExam getStudentExam(Exam exam) {
    return studentExams.stream()
        .filter(studentExam -> studentExam.getExam().equals(exam))
        .findFirst()
        .orElse(null);
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
