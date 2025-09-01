package com.ase.userservice.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String moduleCode;

    private LocalDate examDate;

    private String room;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private String semester;

    // --- getters/setters, no-arg ctor ---

    public Exam() {}

    public Exam(Long id, String title, String moduleCode, LocalDate examDate, String room, ExamType examType, String semester) {
        this.id = id;
        this.title = title;
        this.moduleCode = moduleCode;
        this.examDate = examDate;
        this.room = room;
        this.examType = examType;
        this.semester = semester;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getModuleCode() { return moduleCode; }
    public LocalDate getExamDate() { return examDate; }
    public String getRoom() { return room; }
    public ExamType getExamType() { return examType; }
    public String getSemester() { return semester; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setModuleCode(String moduleCode) { this.moduleCode = moduleCode; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    public void setRoom(String room) { this.room = room; }
    public void setExamType(ExamType examType) { this.examType = examType; }
    public void setSemester(String semester) { this.semester = semester; }
}
