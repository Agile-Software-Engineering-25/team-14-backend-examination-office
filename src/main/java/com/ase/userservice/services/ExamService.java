package com.ase.userservice.services;

import com.ase.userservice.services.dto.ExamDto;
import com.ase.userservice.services.dto.ExamFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {
    Page<ExamDto> search(ExamFilter filter, Pageable pageable);
}
