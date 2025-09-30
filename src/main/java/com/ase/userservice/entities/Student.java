package com.ase.userservice.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 20)
  private String studentId; // Matrikelnummer

  @Column(nullable = false, length = 100)
  private String firstName;

  @Column(nullable = false, length = 100)
  private String lastName;

  @Column(nullable = false, unique = true, length = 150)
  private String email;

  @Column(length = 100)
  private String studyGroup;

  @Column
  private Integer semester;

  @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
  private List<Exam> exams = new ArrayList<>();

  // Konstruktoren
  protected Student() {}

  public Student(String studentId, String firstName, String lastName, String email,
                 String studyGroup, Integer semester) {
      this.studentId = studentId;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.studyGroup = studyGroup;
      this.semester = semester;
  }

  // Getter und Setter
  public Long getId() {
      return id;
  }

  public String getStudentId() {
      return studentId;
  }

  public void setStudentId(String studentId) {
      this.studentId = studentId;
  }

  public String getFirstName() {
      return firstName;
  }

  public void setFirstName(String firstName) {
      this.firstName = firstName;
  }

  public String getLastName() {
      return lastName;
  }

  public void setLastName(String lastName) {
      this.lastName = lastName;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getStudyGroup() {
      return studyGroup;
  }

  public void setStudyGroup(String studyGroup) {
      this.studyGroup = studyGroup;
  }

  public Integer getSemester() {
      return semester;
  }

  public void setSemester(Integer semester) {
      this.semester = semester;
  }

  public List<Exam> getExams() {
      return exams;
  }

  public void setExams(List<Exam> exams) {
      this.exams = exams != null ? new ArrayList<>(exams) : new ArrayList<>();
  }

  // Hilfsmethoden für die Beziehung
  public void addExam(Exam exam) {
      if (!this.exams.contains(exam)) {
          this.exams.add(exam);
          exam.getStudents().add(this);
      }
  }

  public void removeExam(Exam exam) {
      if (this.exams.contains(exam)) {
          this.exams.remove(exam);
          exam.getStudents().remove(this);
      }
  }

  public String getFullName() {
      return firstName + " " + lastName;
  }
}
