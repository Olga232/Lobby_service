package org.example.lobby.domain.service;


import org.example.lobby.web.modelsDTO.LobbyGameDto;
import org.example.lobby.web.modelsDTO.LaunchUrlDto;

import java.util.List;

public interface LobbyGameService {

  List<LobbyGameDto> getListOfAvailableGames(int page, int size, String sessionKey);
  LaunchUrlDto getLaunchURLByLobbyGameIdAndSessionKey(String lobbyGameId, String sessionKey);


}
