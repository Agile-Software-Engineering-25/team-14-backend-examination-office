package com.ase.userservice.dto;

import com.ase.userservice.entities.Student;
import java.util.List;
import java.util.stream.Collectors;

public class StudentResponse {

  private String id;
  private String studentId;
  private String firstName;
  private String lastName;
  private String email;
  private String studyGroup;
  private Integer semester;
  private String fullName;
  private List<String> examIds;

  // Konstruktoren
  public StudentResponse() {}

  public StudentResponse(Student student) {
      this.id = student.getId();
      this.studentId = student.getStudentId();
      this.firstName = student.getFirstName();
      this.lastName = student.getLastName();
      this.email = student.getEmail();
      this.studyGroup = student.getStudyGroup();
      this.semester = student.getSemester();
      this.fullName = student.getFullName();
      this.examIds = student.getExams().stream()
              .map(exam -> exam.getId())
              .collect(Collectors.toList());
  }

  // Getter und Setter
  public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id;
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

  public String getFullName() {
      return fullName;
  }

  public void setFullName(String fullName) {
      this.fullName = fullName;
  }

  public List<String> getExamIds() {
      return examIds;
  }

  public void setExamIds(List<String> examIds) {
      this.examIds = examIds;
  }
}
