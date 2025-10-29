package com.ase.userservice.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.userservice.dto.FeedbackDto;
import com.ase.userservice.dto.StudentExamStateDto;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamState;
import com.ase.userservice.entities.Student;
import com.ase.userservice.entities.StudentExam;
import com.ase.userservice.entities.StudentExamId;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentExamRepository;
import com.ase.userservice.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final StudentExamRepository studentExamRepository;
  private final StudentRepository studentRepository;
  private final ExamRepository examRepository;
  private final BitfrostService bitfrostService;

  @Value("${app.apis.feedback-service.baseurl}")
  private String feedbackServiceBaseUrl;

  private final WebClient feedbackWebClient = WebClient.create();

  private List<FeedbackDto> executeApiCall(String apiPath) {
    return feedbackWebClient.get()
        .uri(feedbackServiceBaseUrl + apiPath)
        .retrieve()
        .bodyToFlux(FeedbackDto.class)
        .collect(Collectors.toList())
        .block();
  }

  public List<FeedbackDto> getFeedbackForLecturer(String lecturerUuid) {
    return executeApiCall("/feedback/for-lecturer/" + lecturerUuid);
  }

  @Transactional(readOnly = true)
  public List<FeedbackDto> getFeedbackForExam(String examUuid) {
    List<FeedbackDto> feedbacks = executeApiCall("/feedback/for-exam/" + examUuid);

    Exam exam = examRepository.findById(examUuid)
        .orElseThrow(() -> new IllegalArgumentException("Exam not found: " + examUuid));

    return feedbacks.stream()
        .peek(fb -> {
          StudentExamId id = new StudentExamId(fb.getStudentUuid(), exam.getId());
          StudentExam studentExam = studentExamRepository.findById(id).orElse(null);
          if (studentExam != null) {
            fb.setState(studentExam.getState());
          }
          else {
            fb.setState(ExamState.EXAM_GRADED);
          }
        })
        .collect(Collectors.toList());
  }

  @Transactional
  public void acceptFeedback(String examUuid, String studentUuid) {
    StudentExamStateDto studentExamStateDto = StudentExamStateDto
        .builder()
        .examUuid(examUuid)
        .studentUuid(studentUuid)
        .state(ExamState.EXAM_ACCEPTED)
        .build();

    setStudentExamState(examUuid, studentUuid, ExamState.EXAM_ACCEPTED);
    bitfrostService.sendRequest("feedback:approve", studentExamStateDto);
  }

  @Transactional
  public void rejectFeedback(String examUuid, String studentUuid) {
    StudentExamStateDto studentExamStateDto = StudentExamStateDto
        .builder()
        .examUuid(examUuid)
        .studentUuid(studentUuid)
        .state(ExamState.EXAM_REJECTED)
        .build();

    setStudentExamState(examUuid, studentUuid, ExamState.EXAM_REJECTED);
    bitfrostService.sendRequest("feedback:reject", studentExamStateDto);
  }

  public void setStudentExamState(String examUuid, String studentUuid, ExamState newState) {
    Student student = studentRepository.findById(studentUuid)
        .orElseThrow(() -> new IllegalArgumentException(
            "Student not found: " + studentUuid
        ));

    Exam exam = examRepository.findById(examUuid)
        .orElseThrow(() -> new IllegalArgumentException(
            "Exam not found: " + examUuid
        ));

    StudentExamId studentExamId = new StudentExamId(student.getId(), exam.getId());
    StudentExam studentExam = studentExamRepository.findById(studentExamId)
        .orElseThrow(() -> new IllegalArgumentException(
            "StudentExam not found for student " + student.getId()
                + " and exam " + exam.getId()
        ));

    studentExam.setState(newState);
    studentExamRepository.save(studentExam);
  }

  @Transactional(readOnly = true)
  public StudentExamStateDto getStudentExamStatus(String examId, String studentId) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Student not found: " + studentId
        ));

    Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Exam not found: " + examId
        ));

    StudentExam studentExam = studentExamRepository.findById(new StudentExamId(
            student.getId(),
            exam.getId()
        ))
        .orElseThrow(() -> new IllegalArgumentException(
            "StudentExam not found for student " + studentId + " and exam " + examId
        ));

    return StudentExamStateDto.builder()
        .studentUuid(student.getId())
        .examUuid(exam.getId())
        .state(studentExam.getState())
        .build();
  }
}
