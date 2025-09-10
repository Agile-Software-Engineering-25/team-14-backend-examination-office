package com.ase.userservice.services;

import com.ase.userservice.controllers.NotFoundException;
import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.repositories.ExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ExamService {
  private final ExamRepository repo;

  public ExamService(ExamRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public ExamResponse create(CreateExamRequest req) {
    req.validateBusinessRules();

    if (repo.existsByModuleCodeAndExamDateAndAttemptNumber(
        req.moduleCode(), req.examDate(), req.attemptNumber())) {
      throw new IllegalStateException("Exam with same moduleCode, examDate and attemptNumber already exists");
    }

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

  @Transactional
  public ExamResponse update(Long id, CreateExamRequest req) {
    req.validateBusinessRules();

    Exam exam = repo.findById(id)
      .orElseThrow(() -> new NotFoundException("Exam " + id + " not found"));

    if (repo.existsByModuleCodeAndExamDateAndAttemptNumberAndIdNot(
        req.moduleCode(), req.examDate(), req.attemptNumber(), id)) {
      throw new IllegalStateException("Exam with same moduleCode, examDate and attemptNumber already exists");
    }

    exam.setTitle(req.title());
    exam.setModuleCode(req.moduleCode());
    exam.setExamDate(req.examDate());
    exam.setRoom(req.room());
    exam.setExamType(req.examType());
    exam.setSemester(req.semester());
    exam.setEcts(req.ects());
    exam.setMaxPoints(req.maxPoints());
    exam.setDuration(req.duration());
    exam.setAttemptNumber(req.attemptNumber());
    exam.setFileUploadRequired(req.fileUploadRequired());
    exam.setTools(req.tools());

    return toResponse(repo.save(exam));
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
      e.getTools()
    );
  }

  @Transactional(readOnly = true)
  public ExamResponse get(Long id) {
    var exam = repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Exam " + id + " not found"));
    return toResponse(exam);
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> list() {
    return repo.findAll().stream().map(ExamService::toResponse).toList();
  }
}
