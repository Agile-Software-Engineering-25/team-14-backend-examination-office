package com.ase.userservice.services;

import com.ase.userservice.dto.CreateExamRequest;
import com.ase.userservice.dto.ExamResponse;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.controllers.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamService {
  private final ExamRepository repo;

  public ExamService(ExamRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public ExamResponse create(CreateExamRequest req) {
    req.validateBusinessRules();
    if (repo.existsByTitleAndDate(req.title(), req.date())) {
      throw new IllegalStateException("Exam with same title and date already exists");
    }
    Exam exam = new Exam(
      req.title(), req.moduleCode(), req.date(), req.startTime(), req.endTime(),
      req.examiner(), req.room(), req.capacity(), req.ects(),
      req.registrationDeadline(), req.deregistrationDeadline()
    );
    Exam saved = repo.save(exam);
    return toResponse(saved);
  }

  @Transactional
  public ExamResponse update(Long id, CreateExamRequest req) {
    req.validateBusinessRules();

    Exam exam = repo.findById(id)
      .orElseThrow(() -> new NotFoundException("Exam " + id + " not found"));

    // Duplikate verhindern (außer beim gleichen Datensatz)
    if (repo.existsByTitleAndDateAndIdNot(req.title(), req.date(), id)) {
      throw new IllegalStateException("Exam with same title and date already exists");
    }

    // Felder 1:1 wie bei Erstellung
    exam.setTitle(req.title());
    exam.setModuleCode(req.moduleCode());
    exam.setDate(req.date());
    exam.setStartTime(req.startTime());
    exam.setEndTime(req.endTime());
    exam.setExaminer(req.examiner());
    exam.setRoom(req.room());
    exam.setCapacity(req.capacity());
    exam.setEcts(req.ects());
    exam.setRegistrationDeadline(req.registrationDeadline());
    exam.setDeregistrationDeadline(req.deregistrationDeadline());

    // exam ist gemanagt; speichern nicht zwingend nötig, aber explizit ist okay:
    Exam saved = repo.save(exam);
    return toResponse(saved);
  }

  public static ExamResponse toResponse(Exam e) {
    return new ExamResponse(
      e.getId(), e.getTitle(), e.getModuleCode(), e.getDate(),
      e.getStartTime(), e.getEndTime(), e.getExaminer(), e.getRoom(),
      e.getCapacity(), e.getEcts(), e.getRegistrationDeadline(), e.getDeregistrationDeadline()
    );
  }
}
