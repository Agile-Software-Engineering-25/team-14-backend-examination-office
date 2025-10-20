package com.ase.userservice.entities;

public enum ExamState {
  EXAM_OPEN, // Student can still submit Files / Exam date has still to come
  EXAM_PENDING, // Exam is awaiting grading by the lecturer
  EXAM_GRADED, // Exam is awaiting confirmation by the examination office
  EXAM_ACCEPTED, // Exam submission has been accepted by examination office
  EXAM_REJECTED, // Exam submission has been rejected by examination office
  EXAM_EXPIRED // Exam date has passed without submission
}
