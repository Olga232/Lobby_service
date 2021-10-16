package org.example.lobby.domain.service.impl;

import org.example.lobby.domain.models.servicesModels.LaunchUrlFromGameRepo;
import org.example.lobby.domain.service.GameRepoCommunicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class GameRepoCommunicationServiceImpl implements GameRepoCommunicationService {

  private final RestTemplate restTemplate;
  @Value("${game-repo.url}")
  private String baseUrlGameRepo;

  public GameRepoCommunicationServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(500))
        .setReadTimeout(Duration.ofSeconds(500))
        .build();
  }

  public LaunchUrlFromGameRepo getLaunchURLByLobbyGameIdAndSessionKey(String lobbyGameId,
                                                                      String sessionKey,
                                                                      String hash) {
    String urlGameRepo = String.format(baseUrlGameRepo, lobbyGameId, sessionKey, hash);
    ResponseEntity<LaunchUrlFromGameRepo> responseFromGameRepo =
        restTemplate.getForEntity(urlGameRepo, LaunchUrlFromGameRepo.class);
    return responseFromGameRepo.getBody();
  }


}
