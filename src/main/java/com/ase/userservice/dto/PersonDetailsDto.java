package com.ase.userservice.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDetailsDto {
  private String id;
  private LocalDate dateOfBirth;
  private String address;
  private String phoneNumber;
  private String photoUrl;
  private String title;

  @JsonProperty("drives_car")
  private boolean drivesCar;

  // Additional fields coming from Keycloak
  private String username;
  private String firstName;
  private String lastName;
  private String email;
}
