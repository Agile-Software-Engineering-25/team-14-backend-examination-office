package com.ase.userservice.entities;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "exams")
public class Exam {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String title;

  @Column(nullable = false, length = 40)
  private String moduleCode;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private LocalTime startTime;

  @Column(nullable = false)
  private LocalTime endTime;

  @Column(nullable = false, length = 80)
  private String examiner;

  @Column(length = 80)
  private String room;

  @Column(nullable = false)
  private Integer capacity;

  @Column
  private Integer ects;

  @Column(nullable = false)
  private LocalDate registrationDeadline;

  @Column(nullable = false)
  private LocalDate deregistrationDeadline;

  protected Exam() {}

  public Exam(String title, String moduleCode, LocalDate date, LocalTime startTime, LocalTime endTime,
              String examiner, String room, Integer capacity, Integer ects,
              LocalDate registrationDeadline, LocalDate deregistrationDeadline) {
    this.title = title;
    this.moduleCode = moduleCode;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.examiner = examiner;
    this.room = room;
    this.capacity = capacity;
    this.ects = ects;
    this.registrationDeadline = registrationDeadline;
    this.deregistrationDeadline = deregistrationDeadline;
  }

  public Long getId() { return id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getModuleCode() { return moduleCode; }
  public void setModuleCode(String moduleCode) { this.moduleCode = moduleCode; }
  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }
  public LocalTime getStartTime() { return startTime; }
  public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
  public LocalTime getEndTime() { return endTime; }
  public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
  public String getExaminer() { return examiner; }
  public void setExaminer(String examiner) { this.examiner = examiner; }
  public String getRoom() { return room; }
  public void setRoom(String room) { this.room = room; }
  public Integer getCapacity() { return capacity; }
  public void setCapacity(Integer capacity) { this.capacity = capacity; }
  public Integer getEcts() { return ects; }
  public void setEcts(Integer ects) { this.ects = ects; }
  public LocalDate getRegistrationDeadline() { return registrationDeadline; }
  public void setRegistrationDeadline(LocalDate registrationDeadline) { this.registrationDeadline = registrationDeadline; }
  public LocalDate getDeregistrationDeadline() { return deregistrationDeadline; }
  public void setDeregistrationDeadline(LocalDate deregistrationDeadline) { this.deregistrationDeadline = deregistrationDeadline; }
}
