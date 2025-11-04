package com.ase.userservice.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.userservice.controllers.NotFoundException;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.dto.GroupDto;
import com.ase.userservice.dto.GroupMemberDto;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.Student;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentRepository;
import jakarta.persistence.EntityManager;

@Service
@Transactional
public class StudentService {

  private final StudentRepository studentRepository;
  private final ExamRepository examRepository;
  private final EntityManager em;

  @Value("${app.apis.group-service.baseurl}")
  private String groupServiceBaseUrl;

  @Value("${app.apis.group-service.get-all-groups-path:/Group/getAllGroups}")
  private String getAllGroupsPath;

  private final WebClient groupWebClient = WebClient.create();


  public StudentService(StudentRepository studentRepository,
                        ExamRepository examRepository,
                        EntityManager em) {
    this.studentRepository = studentRepository;
    this.examRepository = examRepository;
    this.em = em;
  }

  @Transactional(readOnly = true)
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }


  @Transactional(readOnly = true)
  public Student getStudentById(UUID id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Student with ID " + id + " not found"));
  }

  @Transactional(readOnly = true)

  public Student getStudentByStudentId(String studentId) {
    return studentRepository.findByMatriculationId(studentId)
        .orElseThrow(() -> new NotFoundException(
            "Student with matriculation number " + studentId + " not found"

        ));
  }


  public Student createStudent(Student student) {
    if (studentRepository.existsByMatriculationId(student.getMatriculationId())) {
      throw new IllegalArgumentException(
          "Student with matriculation number " + student.getMatriculationId() + " already exists"
      );
    }
    if (studentRepository.existsByEmail(student.getEmail())) {
      throw new IllegalArgumentException(
          "Student with email " + student.getEmail() + " already exists"
      );
    }
    return studentRepository.save(student);
  }


  public Student updateStudent(Student student) {
    if (student.getId() == null) {
      throw new IllegalArgumentException("Student ID cannot be null for update");
    }
    Student existing = getStudentById(student.getId());
    existing.updateFrom(student);
    return existing;
  }

  public void deleteStudent(String id) {
    Student student = getStudentById(UUID.fromString(id));
    studentRepository.delete(student);
  }

  @Transactional(readOnly = true)
  public List<Student> searchStudentsByName(String searchTerm) {
    return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        searchTerm,
        searchTerm
    );
  }

  @Transactional(readOnly = true)
  public List<Student> getStudentsByStudyGroup(String studyGroup) {
    List<GroupDto> groups = fetchAllGroups();
    if (groups == null || groups.isEmpty()) {

      throw new IllegalStateException("Externe Gruppen-API lieferte keine Gruppen zurück");
    }

    GroupDto group = groups.stream()
        .filter(g -> g.getName() != null && g.getName().equalsIgnoreCase(studyGroup))
        .findFirst()

        .orElseThrow(() -> new NotFoundException("Gruppe \"" + studyGroup + "\" nicht gefunden"));

    if (group.getUsers() == null || group.getUsers().isEmpty()) {
      return List.of();
    }

    List<UUID> uuids = new ArrayList<>();
    for (GroupMemberDto m : group.getUsers()) {
      if (m.getStudentUuid() != null && !m.getStudentUuid().isBlank()) {
        try {
          uuids.add(UUID.fromString(m.getStudentUuid()));
        }
        catch (IllegalArgumentException ex) {
        }
      }
    }

    List<String> matriculationIds = group.getUsers().stream()
        .map(GroupMemberDto::getMatriculationId)
        .filter(s -> s != null && !s.isBlank())

        .toList();

    List<String> emails = group.getUsers().stream()
        .map(GroupMemberDto::getEmail)
        .filter(s -> s != null && !s.isBlank())
        .toList();

    List<Student> byUuid = uuids.isEmpty() ? List.of() : studentRepository.findByIdIn(uuids);

    Set<UUID> alreadyFoundIds = byUuid.stream()
        .map(Student::getId)
        .collect(Collectors.toCollection(HashSet::new));


    List<Student> byMatric = matriculationIds.isEmpty()
        ? List.of()
        : studentRepository.findByMatriculationIdIn(matriculationIds);

    List<Student> remainingFromMatric = byMatric.stream()
        .filter(s -> !alreadyFoundIds.contains(s.getId()))
        .toList();
    alreadyFoundIds.addAll(remainingFromMatric.stream().map(Student::getId).toList());

    List<Student> byEmail = emails.isEmpty()
        ? List.of()
        : studentRepository.findByEmailIn(emails);
    List<Student> remainingFromEmail = byEmail.stream()
        .filter(s -> !alreadyFoundIds.contains(s.getId()))
        .toList();

    return List.of(

        byUuid,
        remainingFromMatric,
        remainingFromEmail
    ).stream().flatMap(List::stream).toList();
  }

  @Transactional

  public void addStudentToExam(UUID studentId, UUID examId) {
    Student student = em.getReference(Student.class, studentId);
    Exam exam = em.getReference(Exam.class, examId);
    student.addExam(exam);
    studentRepository.save(student);

  }

  @Transactional
  public void removeStudentFromExam(UUID studentId, UUID examId) {
    Student student = em.getReference(Student.class, studentId);
    Exam exam = em.getReference(Exam.class, examId);
    student.removeExam(exam);

    studentRepository.save(student);
  }

  @Transactional(readOnly = true)
  public List<Student> getStudentsByExamId(String examId) {
    return studentRepository.findStudentsByExamId(UUID.fromString(examId));
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> getExamsForStudent(String studentId) {
    Student student = studentRepository.findByIdWithExams(UUID.fromString(studentId))
        .orElseThrow(() -> new NotFoundException("Student not found"));

    return student.getExams().stream()

        .map(ExamService::toResponse)
        .toList();

  }

  private List<GroupDto> fetchAllGroups() {
    return groupWebClient.get()
        .uri(groupServiceBaseUrl + getAllGroupsPath)
        .retrieve()
        .bodyToFlux(GroupDto.class)
        .collectList()
        .block();

  }
}

