package com.ase.userservice.dto;

import java.time.LocalDate;
import java.util.List;
import com.ase.userservice.entities.ExamState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
  private String studentId;
  private String studentName;
  private String examId;
  private String examTitle;
  private ExamState state;

  @JsonProperty("gradedAt")
  private LocalDate gradedAt;

  @JsonProperty("examUuid")
  private String examUuid;

  @JsonProperty("lecturerUuid")
  private String lecturerUuid;

  @JsonProperty("studentUuid")
  private String studentUuid;

  @JsonProperty("submissionUuid")
  private String submissionUuid;

  @JsonProperty("comment")
  private String comment;

  @JsonProperty("points")
  private int points;

  @JsonProperty("grade")
  private float grade;

  @JsonProperty("fileReference")
  private List<FileReferenceDto> fileReference;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FileReferenceDto {
    @JsonProperty("fileUuid")
    private String fileUuid;

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("downloadLink")
    private String downloadLink;
  }
}
