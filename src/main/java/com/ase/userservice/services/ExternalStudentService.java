package com.ase.userservice.services;

import com.ase.userservice.dto.ExternalGroup;
import com.ase.userservice.dto.ExternalGroupResponse;
import com.ase.userservice.dto.ExternalStudentDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Objects;

@Service
public class ExternalStudentService {

  private final RestTemplate restTemplate = new RestTemplate();
  private static final String BASE_URL = "https://sau-portal.de/team-11-api/api/v1";

  public List<ExternalStudentDto> getAllExternalStudents() {
    String url = BASE_URL + "/group";
    ExternalGroupResponse response = restTemplate.getForObject(url, ExternalGroupResponse.class);

    if (response == null || response.getGroups() == null) {
      return List.of();
    }

    // Alle Studenten aus allen Gruppen flach zusammenführen
    return response.getGroups().stream()
        .flatMap(g -> g.getStudents().stream())
        .toList();
  }

  public List<String> getAllGroupNames() {
    String url = BASE_URL + "/group";
    ExternalGroupResponse response = restTemplate.getForObject(url, ExternalGroupResponse.class);

    if (response == null || response.getGroups() == null) {
      return List.of();
    }

    // Gruppennamen extrahieren
    return response.getGroups().stream()
        .map(ExternalGroup::getName)
        .filter(Objects::nonNull)
        .toList();
  }
}
