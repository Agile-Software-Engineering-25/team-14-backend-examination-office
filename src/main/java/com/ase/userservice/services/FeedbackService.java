package com.ase.userservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.userservice.dto.FeedbackDto;
import com.ase.userservice.dto.PersonDetailsDto;
import com.ase.userservice.dto.StudentExamStateDto;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamState;
import com.ase.userservice.entities.StudentExam;
import com.ase.userservice.entities.StudentExamId;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentExamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final StudentExamRepository studentExamRepository;
  private final ExamRepository examRepository;
  private final BitfrostService bitfrostService;
  private final StudentService studentService;
  private final KeycloakService keycloakService;

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

  public List<FeedbackDto> getFeedbackForExam(String examUuid) {
    List<FeedbackDto> feedbacks = executeApiCall("/feedback/for-exam/" + examUuid);

    Exam exam = examRepository.findById(examUuid)
        .orElseThrow(() -> new IllegalArgumentException("Exam not found: " + examUuid));

    String kcToken = keycloakService.getToken();
    Map<String, PersonDetailsDto> userCache = new HashMap<>();

    Function<String, PersonDetailsDto> loadUser = (uuid) -> {
      if (uuid == null) return null;

      return userCache.computeIfAbsent(uuid, (id) -> {
        try {
          return studentService.getUserInfo(id, kcToken);
        } catch (Exception e) {
          return null;
        }
      });
    };

    return feedbacks.stream()
        .peek(fb -> {
          StudentExamId id = new StudentExamId(fb.getStudentUuid(), exam.getId());
          StudentExam studentExam = studentExamRepository.findById(id).orElse(null);

          fb.setState(studentExam != null
              ? studentExam.getState()
              : ExamState.EXAM_GRADED);

          PersonDetailsDto lecturer = loadUser.apply(fb.getLecturerUuid());
          PersonDetailsDto student = loadUser.apply(fb.getStudentUuid());

          String lecturerName;
          if (lecturer != null) {
            String lFirstName = lecturer.getFirstName() == null ? "John" : lecturer.getFirstName();
            String lLastName = lecturer.getLastName() == null ? "Doe" : lecturer.getLastName();

            lecturerName = (lecturer.getTitle() != null && !lecturer.getTitle().isEmpty())
                ? lecturer.getTitle() + " " + lFirstName + " " + lLastName
                : lFirstName + " " + lLastName;
          } else {
            lecturerName = "--Not Found--";
          }

          String studentName;
          if (student != null) {
            String sFirstName = student.getFirstName() == null ? "John" : student.getFirstName();
            String sLastName = student.getLastName() == null ? "Doe" : student.getLastName();

            studentName = sFirstName + " " + sLastName;
          } else {
            studentName = "--Not Found--";
          }

          fb.setLecturerName(lecturerName);
          fb.setStudentName(studentName);
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
    Exam exam = examRepository.findById(examUuid)
        .orElseThrow(() -> new IllegalArgumentException(
            "Exam not found: " + examUuid
        ));

    StudentExamId studentExamId = new StudentExamId(studentUuid, exam.getId());
    StudentExam studentExam = studentExamRepository.findById(studentExamId)
        .orElseThrow(() -> new IllegalArgumentException(
            "StudentExam not found for student " + studentUuid
                + " and exam " + exam.getId()
        ));

    studentExam.setState(newState);
    studentExamRepository.save(studentExam);
  }

  @Transactional(readOnly = true)
  public StudentExamStateDto getStudentExamStatus(String examId, String studentId) {
    Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Exam not found: " + examId
        ));

    StudentExam studentExam = studentExamRepository.findById(new StudentExamId(
            studentId,
            exam.getId()
        ))
        .orElseThrow(() -> new IllegalArgumentException(
            "StudentExam not found for student " + studentId + " and exam " + examId
        ));

    return StudentExamStateDto.builder()
        .studentUuid(studentId)
        .examUuid(exam.getId())
        .state(studentExam.getState())
        .build();
  }
}
