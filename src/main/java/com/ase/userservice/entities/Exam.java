package com.ase.userservice.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.ase.userservice.dto.CreateExamRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, length = 160)
  private String title;

  @Column(name = "module_code", nullable = false, length = 40)
  private String moduleCode;

  @Column(name = "exam_date", nullable = false)
  private LocalDateTime examDate;

  @Column(nullable = false, length = 80)
  private String room;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ExamType examType;

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
  @CollectionTable(name = "exam_tools", joinColumns = @JoinColumn(name = "exam_id"))
  @Column(name = "tool", nullable = false, length = 60)
  private List<String> tools = new ArrayList<>();

  @OneToMany(
      mappedBy = "exam",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<StudentExam> studentExams = new HashSet<>();

  public Exam(String title,
              String moduleCode,
              LocalDateTime examDate,
              String room,
              ExamType examType,
              String semester,
              Integer ects,
              Integer maxPoints,
              Integer duration,
              Integer attemptNumber,
              boolean fileUploadRequired,
              List<String> tools) {
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
    this.tools = tools != null ? new ArrayList<>(tools) : new ArrayList<>();
  }

  public void addStudent(Student student) {
    if (student != null) {
      StudentExam studentExam = new StudentExam();
      studentExam.setStudent(student);
      studentExam.setExam(this);
      studentExams.add(studentExam);
      student.getStudentExams().add(studentExam);
    }
  }

  public void removeStudent(Student student) {
    if (student != null) {
      studentExams.removeIf(studentExam -> {
        if (studentExam.getStudent().equals(student)) {
          student.getStudentExams().remove(studentExam);
          return true;
        }
        return false;
      });
    }
  }

  public Set<Student> getStudents() {
    return studentExams.stream()
        .map(StudentExam::getStudent)
        .collect(java.util.stream.Collectors.toSet());
  }

  public StudentExam getStudentExam(Student student) {
    return studentExams.stream()
        .filter(studentExam -> studentExam.getStudent().equals(student))
        .findFirst()
        .orElse(null);
  }

  public void updateFromRequest(CreateExamRequest req) {
    this.title = req.title();
    this.moduleCode = req.moduleCode();
    this.examDate = req.examDate();
    this.room = req.room();
    this.examType = req.examType();
    this.semester = req.semester();
    this.ects = req.ects();
    this.maxPoints = req.maxPoints();
    this.duration = req.duration();
    this.attemptNumber = req.attemptNumber();
    this.fileUploadRequired = req.fileUploadRequired();
    this.tools = List.copyOf(req.tools());
  }
}
