package org.example.lobby.web.modelsDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LobbyGameDto {

  @JsonProperty("game")
  String internalGameId;


}
