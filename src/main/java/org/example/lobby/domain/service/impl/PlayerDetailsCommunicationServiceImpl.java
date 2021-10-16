package org.example.lobby.domain.service.impl;

import org.example.lobby.domain.models.servicesModels.PlayerDetails;
import org.example.lobby.domain.service.PlayerDetailsCommunicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class PlayerDetailsCommunicationServiceImpl implements PlayerDetailsCommunicationService {

  private final RestTemplate restTemplate;
  @Value("${player-service.url}")
  private String url;
  @Value("${player-service.username}")
  private String username;
  @Value("${player-service.password}")
  private String password;

  public PlayerDetailsCommunicationServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(500))
        .setReadTimeout(Duration.ofSeconds(500))
        .build();
  }

  public PlayerDetails getPlayerDetailsFromPlayerServiceBySessionKey(String sessionKey) {
    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
    ResponseEntity<PlayerDetails> responseFromPlayerService =
        restTemplate.getForEntity(url, PlayerDetails.class, sessionKey);
    return responseFromPlayerService.getBody();
  }


}
