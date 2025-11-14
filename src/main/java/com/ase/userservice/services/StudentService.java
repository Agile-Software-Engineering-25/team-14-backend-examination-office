package com.ase.userservice.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.dto.GroupDto;
import com.ase.userservice.dto.GroupResponseDto;
import com.ase.userservice.dto.StudentDto;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamState;
import com.ase.userservice.entities.StudentExam;
import com.ase.userservice.entities.StudentExamId;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentExamRepository;

@Service
@Transactional
public class StudentService {
  @Value("${app.apis.student-service.baseurl}")
  private String externalStudentServiceBaseUrl;

  private final WebClient externalStudentWebClient = WebClient.create();
  private final ExamRepository examRepository;
  private final StudentExamRepository studentExamRepository;
  private final KeycloakService keycloakService;

  public StudentService(ExamRepository examRepository,
                        StudentExamRepository studentExamRepository,
                        KeycloakService keycloakService) {
    this.examRepository = examRepository;
    this.studentExamRepository = studentExamRepository;
    this.keycloakService = keycloakService;
  }

  public List<GroupDto> getStudyGroups() {
    GroupResponseDto groupResponse = externalStudentWebClient.get()
        .uri(externalStudentServiceBaseUrl + "/group?withDetails=true")
        .header("Authorization", keycloakService.getToken())
        .retrieve()
        .bodyToMono(GroupResponseDto.class)
        .block();

    if (groupResponse == null) {
      return List.of();
    }

    return groupResponse.getGroups();
  }

  public List<GroupDto> getStudyGroupsForExam(String examUuid) {
    List<GroupDto> groups = getStudyGroups();
    for (GroupDto group : groups) {
      if (group.getStudents() != null) {
        for (StudentDto student : group.getStudents()) {
          student.setEnlisted(studentExamRepository.findById(
              new StudentExamId(student.getUuid(), examUuid)).isPresent()
          );
        }
      }
    }

    return groups;
  }

  public List<StudentDto> getStudentsByStudyGroup(String studyGroup) {
    GroupDto group = externalStudentWebClient.get()
        .uri(externalStudentServiceBaseUrl + "/group/" + studyGroup)
        .header("Authorization", keycloakService.getToken())
        .retrieve()
        .bodyToMono(GroupDto.class)
        .block();

    if (group == null) {
      return List.of();
    }

    return group.getStudents();
  }

  public List<StudentDto> getAllStudents() {
    return getStudyGroups()
        .stream()
        .flatMap(group -> group.getStudents().stream())
        .collect(Collectors.toList());
  }

  @Transactional
  public void addStudentToExam(String studentId, String examId) {
    Exam exam = examRepository.findById(examId).orElseThrow();
    StudentExam studentExam = new StudentExam(
        new StudentExamId(studentId, exam.getId()),
        exam,
        ExamState.EXAM_OPEN
    );

    studentExamRepository.save(studentExam);
  }

  @Transactional
  public void removeStudentFromExam(String studentId, String examId) {
    StudentExam studentExam = studentExamRepository
        .findById(new StudentExamId(studentId, examId))
        .orElseThrow();

    studentExamRepository.delete(studentExam);
  }

  @Transactional(readOnly = true)
  public List<String> getStudentIdsByExamId(String examId) {
    return studentExamRepository
        .findByIdExamId(examId)
        .stream()
        .map(StudentExam::getStudentUuid)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> getExamsForStudent(String studentId) {
    return studentExamRepository
        .findByIdStudentId(studentId)
        .stream()
        .map(studentExam -> ExamResponse.from(studentExam.getExam()))
        .collect(Collectors.toList());
  }

  public StudentDto getStudentInfo(String studentId) {
    return externalStudentWebClient.get()
        .uri(externalStudentServiceBaseUrl + "/users/" + studentId + "?withDetails=true")
        .header("Authorization", keycloakService.getToken())
        .retrieve()
        .bodyToMono(StudentDto.class)
        .block();
  }
}
