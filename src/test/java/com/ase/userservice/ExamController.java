package com.ase.userservice.controllers;


import com.ase.userservice.dto.CreateExamRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExamControllerIT {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @Test
  void createExam_returns201() throws Exception {

    CreateExamRequest req = new CreateExamRequest(
      "SE I – Klausur", "ASE-101", LocalDate.now().plusDays(30),
      LocalTime.of(9,0), LocalTime.of(11,0),
      "Prof. Ada Lovelace", "HS A", 50, 5,
      LocalDate.now().plusDays(25), LocalDate.now().plusDays(27)
    );

    mvc.perform(post("/api/exams")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(req)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").exists());
  }
}
