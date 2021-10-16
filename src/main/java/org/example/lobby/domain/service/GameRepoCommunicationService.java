package org.example.lobby.domain.service;


import org.example.lobby.domain.models.servicesModels.LaunchUrlFromGameRepo;

public interface GameRepoCommunicationService {

  LaunchUrlFromGameRepo getLaunchURLByLobbyGameIdAndSessionKey(String lobbyGameId, String sessionKey, String hash);


}
