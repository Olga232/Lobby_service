package org.example.lobby.domain.service;


import org.example.lobby.domain.models.servicesModels.PlayerDetails;

public interface PlayerDetailsCommunicationService {

  PlayerDetails getPlayerDetailsFromPlayerServiceBySessionKey(String sessionKey);


}
