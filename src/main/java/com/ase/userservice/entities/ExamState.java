package com.ase.userservice.entities;

public enum ExamState {
  EXAM_OPEN, // Student can still submit Files / Exam date has still to come
  EXAM_PENDING, // Exam is awaiting grading by the lecturer
  EXAM_GRADED, // Exam is awaiting confirmation by the examination office
  EXAM_FINISHED // Exam is graded & confirmed
}
