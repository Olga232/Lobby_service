package org.example.lobby.web.controllers;

import org.example.lobby.web.modelsDTO.LaunchUrlDto;
import org.example.lobby.web.modelsDTO.LobbyGameDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface LobbyController {

  @GetMapping(path = "/available-games")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  List<LobbyGameDto> getListOfAvailableGames(@RequestParam(name = "page",
      required=false, defaultValue="0") int page, @RequestParam(name = "size",
      required=false, defaultValue="5") int size, @RequestBody String sessionKey);

  @GetMapping(path = "/launch")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  LaunchUrlDto getLaunchURL(@RequestParam(name = "lobbyGameId") String lobbyGameId,
                            @RequestParam(name = "sessionKey") String sessionKey);


}
