package com.ase.userservice.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ase.userservice.controllers.NotFoundException;
import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.repositories.ExamRepository;

@Service
@Transactional
public class ExamService {

  private final ExamRepository repo;

  public ExamService(ExamRepository repo) {
    this.repo = repo;
  }

  public ExamResponse create(CreateExamRequest req) {
    checkDuplicate(req.moduleCode(), req.examDate(), req.attemptNumber(), null);

    Exam exam = new Exam(
        req.title(),
        req.moduleCode(),
        req.examDate(),
        req.room(),
        req.examType(),
        req.semester(),
        req.ects(),
        req.maxPoints(),
        req.duration(),
        req.attemptNumber(),
        req.fileUploadRequired(),
        req.tools()
    );

    return toResponse(repo.save(exam));
  }

  public ExamResponse update(String id, CreateExamRequest req) {
    Exam exam = repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Exam " + id + " not found"));

    checkDuplicate(req.moduleCode(), req.examDate(), req.attemptNumber(), id);

    exam.updateFromRequest(req);

    return toResponse(repo.save(exam));
  }

  @Transactional(readOnly = true)
  public ExamResponse get(String id) {
    return repo.findById(id)
        .map(ExamService::toResponse)
        .orElseThrow(() -> new NotFoundException("Exam " + id + " not found"));
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> list() {
    return repo.findAll().stream()
        .map(ExamService::toResponse)
        .toList();
  }

  public void delete(String id) {
    if (!repo.existsById(id)) {
      throw new NotFoundException("Exam " + id + " not found");
    }
    repo.deleteById(id);
  }

  private void checkDuplicate(String moduleCode,
                              java.time.LocalDateTime examDate,
                              Integer attemptNumber,
                              String excludeId) {
    boolean exists = (excludeId == null)
        ? repo.existsByModuleCodeAndExamDateAndAttemptNumber(
        moduleCode,
        examDate,
        attemptNumber)
        : repo.existsByModuleCodeAndExamDateAndAttemptNumberAndIdNot(
        moduleCode,
        examDate,
        attemptNumber,
        excludeId);

    if (exists) {
      throw new IllegalStateException(
          "Exam with same moduleCode, examDate and attemptNumber already exists");
    }
  }

  public static ExamResponse toResponse(Exam e) {
    return new ExamResponse(
        e.getId(),
        e.getTitle(),
        e.getModuleCode(),
        e.getExamDate(),
        e.getRoom(),
        e.getExamType(),
        e.getSemester(),
        e.getEcts(),
        e.getMaxPoints(),
        e.getDuration(),
        e.getAttemptNumber(),
        e.isFileUploadRequired(),
        e.getExamState(),
        e.getTools()
    );
  }
}
