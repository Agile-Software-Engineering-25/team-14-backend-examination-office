package com.ase.userservice.controllers;

import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.dto.ExamDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExamController.class)
class ExamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExamService examService;

    @Test
    void search_byQuery_returns200AndResults() throws Exception {
        List<ExamDto> content = List.of(
            new ExamDto(1L, "Algorithmen", "ASE101",
                LocalDate.of(2025,11,15),
                "H-101", "WRITTEN", "WS25")
        );
        Page<ExamDto> page = new PageImpl<>(content);

        Mockito.when(examService.search(any(), any())).thenReturn(page);

        mockMvc.perform(get("/v1/exams")
                .param("query","algo")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].title").value("Algorithmen"))
            .andExpect(jsonPath("$.content[0].moduleCode").value("ASE101"))
            .andExpect(jsonPath("$.content[0].room").value("H-101"));
    }

    @Test
    void invalid_dateRange_returns400WithMessage() throws Exception {
        mockMvc.perform(get("/v1/exams")
                .param("examDateFrom","2025-12-31")
                .param("examDateTo","2025-01-01"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("INVALID_DATE_RANGE"));
    }

    @Test
    void combined_filters_areApplied_andPaged() throws Exception {
        List<ExamDto> content = List.of(
            new ExamDto(2L, "SE Praxis", "ASE201",
                LocalDate.of(2025,10,10),
                "G-203", "ORAL", "WS25")
        );
        Page<ExamDto> page = new PageImpl<>(content);

        Mockito.when(examService.search(any(), any())).thenReturn(page);

        mockMvc.perform(get("/v1/exams")
                .param("moduleCode","ASE201")
                .param("room","G-203")
                .param("page","1")
                .param("size","5")
                .param("sort","examDate,asc"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].moduleCode").value("ASE201"))
            .andExpect(jsonPath("$.content[0].room").value("G-203"));
    }

    @Test
    void pageable_and_sort_are_passed_to_service() throws Exception {
        Mockito.when(examService.search(any(), any()))
            .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/v1/exams")
                .param("page","1")
                .param("size","5")
                .param("sort","examDate,asc"))
            .andExpect(status().isOk());

        var pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(examService).search(any(), pageableCaptor.capture());
        Pageable p = pageableCaptor.getValue();

        assert p.getPageNumber() == 1;
        assert p.getPageSize() == 5;
        Sort.Order o = p.getSort().getOrderFor("examDate");
        assert o != null && o.getDirection() == Sort.Direction.ASC;
    }

    @Test
    void examType_filter_valid_returns200() throws Exception {
        List<ExamDto> content = List.of(
            new ExamDto(10L, "SE Praxis", "ASE201",
                LocalDate.of(2025,10,10),
                "G-203", "ORAL", "WS25")
        );
        Mockito.when(examService.search(any(), any()))
            .thenReturn(new PageImpl<>(content));

        mockMvc.perform(get("/v1/exams").param("examType","ORAL"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].examType").value("ORAL"));
    }

    @Test
    void examType_filter_invalid_returns400() throws Exception {
        mockMvc.perform(get("/v1/exams").param("examType","WRONGTYPE"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("INVALID_EXAM_TYPE"));
    }

    @Test
    void invalid_date_format_returns400_with_parameter_name() throws Exception {
        mockMvc.perform(get("/v1/exams").param("examDateFrom","2025-13-01"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("INVALID_PARAMETER: examDateFrom"));
    }
}
