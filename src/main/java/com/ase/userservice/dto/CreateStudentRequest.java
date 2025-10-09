package com.ase.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateStudentRequest {

  @NotBlank(message = "Student ID ist erforderlich")
  private String studentId;

  @NotBlank(message = "Vorname ist erforderlich")
  private String firstName;

  @NotBlank(message = "Nachname ist erforderlich")
  private String lastName;

  @NotBlank(message = "E-Mail ist erforderlich")
  @Email(message = "Gültige E-Mail-Adresse erforderlich")
  private String email;

  private String studyGroup;

  private Integer semester;

  // Konstruktoren
  public CreateStudentRequest() {}

  public CreateStudentRequest(String studentId, String firstName, String lastName,
                             String email, String studyGroup, Integer semester) {
      this.studentId = studentId;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.studyGroup = studyGroup;
      this.semester = semester;
  }

  // Getter und Setter
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
}
