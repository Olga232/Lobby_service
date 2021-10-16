package org.example.lobby.web.controllers.impl;

import org.example.lobby.domain.service.LobbyGameService;
import org.example.lobby.web.controllers.LobbyController;
import org.example.lobby.web.modelsDTO.LaunchUrlDto;
import org.example.lobby.web.modelsDTO.LobbyGameDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class LobbyControllerImpl implements LobbyController {

  static final Logger log = LoggerFactory.getLogger(LobbyControllerImpl.class);
  private final LobbyGameService lobbyGameService;

  public LobbyControllerImpl(LobbyGameService lobbyGameService) {
    this.lobbyGameService = lobbyGameService;
  }

  @Override
  public List<LobbyGameDto> getListOfAvailableGames(int page, int size, String sessionKey) {
    log.info("Get request \"/available-games\", page # " + page + ", size: " + size +
        " from player_session_key: " + sessionKey);
    return lobbyGameService.getListOfAvailableGames(page, size, sessionKey);
  }

  @Override
  public LaunchUrlDto getLaunchURL(String lobbyGameId, String sessionKey) {
    log.info("Get request \"/launch\" lobby_game_id: " + lobbyGameId +
        " from player_session_key: " + sessionKey);
    return lobbyGameService.getLaunchURLByLobbyGameIdAndSessionKey(lobbyGameId, sessionKey);
  }


}
