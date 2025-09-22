package com.ase.userservice.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "exams",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_exam_modcode_date_attempt",
            columnNames = {"module_code", "exam_date", "attempt_number"}
        )
    }
)
public class Exam {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 160)
  private String title;

  @Column(name = "module_code", nullable = false, length = 40)
  private String moduleCode;

  @Column(name = "exam_date", nullable = false)
  private LocalDateTime examDate;

  @Column(nullable = false, length = 80)
  private String room;

  @Column(nullable = false, length = 40)
  private String examType;

  @Column(nullable = false, length = 20)
  private String semester;

  @Column(nullable = false)
  private Integer ects;

  @Column(nullable = false)
  private Integer maxPoints;

  @Column(nullable = false)
  private Integer duration;

  @Column(name = "attempt_number", nullable = false)
  private Integer attemptNumber;

  @Column(nullable = false)
  private boolean fileUploadRequired;

  @ElementCollection
  @CollectionTable(
      name = "exam_tools",
      joinColumns = @JoinColumn(name = "exam_id")
  )
  @Column(name = "tool", nullable = false, length = 60)
  private List<String> tools = new ArrayList<>();

  protected Exam() {
  }

  public Exam(
      String title,
      String moduleCode,
      LocalDateTime examDate,
      String room,
      String examType,
      String semester,
      Integer ects,
      Integer maxPoints,
      Integer duration,
      Integer attemptNumber,
      boolean fileUploadRequired,
      List<String> tools
  ) {
    this.title = title;
    this.moduleCode = moduleCode;
    this.examDate = examDate;
    this.room = room;
    this.examType = examType;
    this.semester = semester;
    this.ects = ects;
    this.maxPoints = maxPoints;
    this.duration = duration;
    this.attemptNumber = attemptNumber;
    this.fileUploadRequired = fileUploadRequired;
    if (tools != null) {
      this.tools = new ArrayList<>(tools);
    }
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getModuleCode() {
    return moduleCode;
  }

  public void setModuleCode(String moduleCode) {
    this.moduleCode = moduleCode;
  }

  public LocalDateTime getExamDate() {
    return examDate;
  }

  public void setExamDate(LocalDateTime examDate) {
    this.examDate = examDate;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public Integer getEcts() {
    return ects;
  }

  public void setEcts(Integer ects) {
    this.ects = ects;
  }

  public Integer getMaxPoints() {
    return maxPoints;
  }

  public void setMaxPoints(Integer maxPoints) {
    this.maxPoints = maxPoints;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  public boolean isFileUploadRequired() {
    return fileUploadRequired;
  }

  public void setFileUploadRequired(boolean fileUploadRequired) {
    this.fileUploadRequired = fileUploadRequired;
  }

  public List<String> getTools() {
    return tools;
  }

  public void setTools(List<String> tools) {
    this.tools = tools != null ? new ArrayList<>(tools) : new ArrayList<>();
  }
}
