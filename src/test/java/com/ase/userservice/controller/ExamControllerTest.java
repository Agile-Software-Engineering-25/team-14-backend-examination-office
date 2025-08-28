@WebMvcTest(ExamController.class)
class ExamControllerTest {

  @Autowired MockMvc mockMvc;
  @MockBean ExamService examService;

  @Test
  void searchByQuery_returns200AndResults() throws Exception {
    // Arrange
    List<ExamDto> dto = List.of(
      new ExamDto(1L,"Algorithmen","ASE101","2025-11-15","H-101","WRITTEN","WS25")
    );
    when(examService.search(any())).thenReturn(new PageImpl<>(dto));

    // Act + Assert
    mockMvc.perform(get("/v1/exams").param("query","algo"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content[0].title").value("Algorithmen"))
      .andExpect(jsonPath("$.content[0].moduleCode").value("ASE101"));
  }

  @Test
  void invalidDateRange_returns400() throws Exception {
    mockMvc.perform(get("/v1/exams")
      .param("examDateFrom","2025-12-31")
      .param("examDateTo","2025-01-01"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error").value("INVALID_DATE_RANGE"));
  }
}
