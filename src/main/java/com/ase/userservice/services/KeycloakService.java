package com.ase.userservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.userservice.dto.AuthTokenResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakService {
  @Value("${app.apis.keycloak.baseurl}")
  private String keycloakBaseUrl;

  @Value("${app.apis.keycloak.auth_client_id}")
  private String authClientId;

  @Value("${app.apis.keycloak.auth_client_secret}")
  private String authClientSecret;

  private final WebClient keycloakWebClient = WebClient.create();

  private AuthTokenResponseDto executeApiCall(String apiPath) {
    return keycloakWebClient.post()
        .uri(keycloakBaseUrl + apiPath)
        .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(
            BodyInserters.fromFormData("client_id", authClientId)
                .with("client_secret", authClientSecret)
                .with("grant_type", "client_credentials")
        )
        .retrieve()
        .bodyToMono(AuthTokenResponseDto.class)
        .block();
  }

  public String getToken() {
    AuthTokenResponseDto tokenResponse = executeApiCall("/token");
    System.out.println(String.format("%s %s", tokenResponse.getTokenType(), tokenResponse.getAccessToken()));
    return String.format("%s %s", tokenResponse.getTokenType(), tokenResponse.getAccessToken());
  }
}
