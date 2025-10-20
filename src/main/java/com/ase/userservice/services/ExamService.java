package com.ase.userservice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ase.userservice.controllers.NotFoundException;
import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamState;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.repositories.StudentExamRepository;

@Service
@Transactional
public class ExamService {

  private final ExamRepository repo;
  private final StudentExamRepository studentExamRepository;

  public ExamService(ExamRepository repo, StudentExamRepository studentExamRepository) {
    this.repo = repo;
    this.studentExamRepository = studentExamRepository;
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
    Optional<Exam> e = repo.findById(id);
    if (e.isEmpty()) {
      throw new NotFoundException("Exam " + id + " not found");
    }

    Integer submissionCount = studentExamRepository.countByExamAndState(
        e.get(),
        ExamState.EXAM_GRADED
    );

    return ExamService.toResponse(e.get(), submissionCount);
  }

  @Transactional(readOnly = true)
  public List<ExamResponse> list() {
    return repo.findAll().stream()
        .map(exam -> ExamService.toResponse(exam,
            studentExamRepository.countByExamAndState(
                exam,
                ExamState.EXAM_GRADED
            )
        ))
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
    return ExamService.toResponse(e, 0);
  }

  public static ExamResponse toResponse(Exam e, Integer submissionCount) {
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
        e.getTools(),
        submissionCount
    );
  }
}
