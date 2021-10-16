package org.example.lobby.domain.modelTransformer.impl;

import org.example.lobby.domain.models.repositoryEntity.LobbyGame;
import org.example.lobby.domain.modelTransformer.ModelTransformer;
import org.example.lobby.web.modelsDTO.LobbyGameDto;
import org.springframework.stereotype.Component;

@Component
public class LobbyGameTransformer implements ModelTransformer<LobbyGame, LobbyGameDto> {

  @Override
  public LobbyGameDto asDto(LobbyGame lobbyGame) {
    return new LobbyGameDto(lobbyGame.getInternalGameId());
  }
}
