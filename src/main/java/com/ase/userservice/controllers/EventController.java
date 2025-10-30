package com.ase.userservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.userservice.dto.FeedbackDto;
import com.ase.userservice.entities.ExamState;
import com.ase.userservice.services.FeedbackService;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    allowCredentials = "true",
    maxAge = 3600
)
@RestController
@RequestMapping("/api/events")
public class EventController {
  private final FeedbackService feedbackService;

  public EventController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PostMapping("/feedbacks/submit")
  private void feedbackSubmitCallback(@RequestBody FeedbackDto[] feedbacks) {
    for (FeedbackDto feedback : feedbacks) {
      feedbackService.setStudentExamState(
          feedback.getStudentUuid(),
          feedback.getExamUuid(),
          ExamState.EXAM_GRADED
      );
    }
  }
}
