package com.ase.userservice.services.impl;

import com.ase.userservice.entities.Exam;
import com.ase.userservice.entities.ExamType;
import com.ase.userservice.repositories.ExamRepository;
import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.dto.ExamDto;
import com.ase.userservice.services.dto.ExamFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository repo;

    public ExamServiceImpl(ExamRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<ExamDto> search(ExamFilter f, Pageable pageable) {
        Specification<Exam> spec = (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            f.query().ifPresent(q -> {
                var like = "%" + q.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("moduleCode")), like)
                ));
            });

            f.moduleCode().ifPresent(mc ->
                    predicates.add(cb.equal(cb.lower(root.get("moduleCode")), mc.toLowerCase()))
            );

            f.room().ifPresent(r ->
                    predicates.add(cb.equal(cb.lower(root.get("room")), r.toLowerCase()))
            );

            f.examType().ifPresent(et ->
                    predicates.add(cb.equal(root.get("examType"), ExamType.valueOf(et)))
            );

            f.semester().ifPresent(s ->
                    predicates.add(cb.equal(cb.lower(root.get("semester")), s.toLowerCase()))
            );

            f.examDateFrom().ifPresent(from ->
                    predicates.add(cb.greaterThanOrEqualTo(root.get("examDate"), from))
            );

            f.examDateTo().ifPresent(to ->
                    predicates.add(cb.lessThanOrEqualTo(root.get("examDate"), to))
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repo.findAll(spec, pageable).map(this::toDto);
    }

    private ExamDto toDto(Exam e) {
        return new ExamDto(
                e.getId(),
                e.getTitle(),
                e.getModuleCode(),
                e.getExamDate(),
                e.getRoom(),
                e.getExamType() != null ? e.getExamType().name() : null,
                e.getSemester()
        );
    }
}
