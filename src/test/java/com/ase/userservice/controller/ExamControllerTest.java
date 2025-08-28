package com.ase.userservice.controller;

import com.ase.userservice.controllers.ExamController;
import com.ase.userservice.services.ExamService;
import com.ase.userservice.services.dto.ExamDto;
import com.ase.userservice.services.dto.ExamFilter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExamController.class)
class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    @Test
    void searchByQuery_returns200AndResults() throws Exception {
        // Arrange
        List<ExamDto> content = List.of(
                new ExamDto(1L, "Algorithmen", "ASE101",
                        LocalDate.of(2025, 11, 15), "H-101", "WRITTEN", "WS25")
        );
        Page<ExamDto> page = new PageImpl<>(content, PageRequest.of(0, 20), 1);
        when(examService.search(any(ExamFilter.class), any())).thenReturn(page);

        // Act + Assert
        mockMvc.perform(get("/v1/exams")
                        .param("query", "algo")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "examDate,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("Algorithmen")))
                .andExpect(jsonPath("$.content[0].moduleCode", is("ASE101")))
                .andExpect(jsonPath("$.pageable.paged", is(true)));

        // Optional: prüfen, dass Filter korrekt durchgereicht wurde
        ArgumentCaptor<ExamFilter> captor = ArgumentCaptor.forClass(ExamFilter.class);
        verify(examService, times(1)).search(captor.capture(), any());
        ExamFilter passed = captor.getValue();
        // kleine Sanity-Checks
        assert passed.query().equals(Optional.of("algo"));
    }

    @Test
    void invalidDateRange_returns400() throws Exception {
        mockMvc.perform(get("/v1/exams")
                        .param("examDateFrom", "2025-12-31")
                        .param("examDateTo", "2025-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("INVALID_DATE_RANGE")));
    }
}
