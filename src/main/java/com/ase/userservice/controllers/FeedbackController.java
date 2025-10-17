package com.ase.userservice.controllers;

import com.ase.userservice.dto.FeedbackDto;
import com.ase.userservice.dto.StudentExamStateDto;
import com.ase.userservice.entities.StudentExam;
import com.ase.userservice.services.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksForExam(@PathVariable String examId) {
        try {
            List<FeedbackDto> feedbacks = feedbackService.getFeedbackForExam(examId);
            return ResponseEntity.ok(feedbacks);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/exam/{examId}/student/{studentId}/accept")
    public ResponseEntity<Void> acceptFeedback(
            @PathVariable String examId,
            @PathVariable String studentId) {
        feedbackService.acceptFeedback(examId, studentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/exam/{examId}/student/{studentId}/reject")
    public ResponseEntity<FeedbackDto> rejectFeedback(
            @PathVariable String examId,
            @PathVariable String studentId) {
        feedbackService.rejectFeedback(examId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("exam/{examId}/student/{studentId}/status")
  public ResponseEntity<StudentExamStateDto> feedbackStatus(
      @PathVariable String examId,
      @PathVariable String studentId) {
      return ResponseEntity.ok(feedbackService.getStudentExamStatus(examId, studentId));
    }
}
