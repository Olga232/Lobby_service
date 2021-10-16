package org.example.lobby.domain.service.impl;

import org.example.lobby.domain.modelTransformer.ModelTransformer;
import org.example.lobby.domain.models.repositoryEntity.LobbyGame;
import org.example.lobby.domain.models.servicesModels.LaunchUrlFromGameRepo;
import org.example.lobby.domain.models.servicesModels.PlayerDetails;
import org.example.lobby.domain.repository.LobbyGameRepository;
import org.example.lobby.domain.service.GameRepoCommunicationService;
import org.example.lobby.domain.service.LobbyGameService;
import org.example.lobby.domain.service.PlayerDetailsCommunicationService;
import org.example.lobby.web.exceptions.EmptyPageException;
import org.example.lobby.web.exceptions.GenerateHashException;
import org.example.lobby.web.exceptions.NoInternalGameIdInDBException;
import org.example.lobby.web.modelsDTO.LobbyGameDto;
import org.example.lobby.web.modelsDTO.LaunchUrlDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LobbyGameServiceImpl implements LobbyGameService {

  private static final String EMPTY_PAGE_MESSAGE = "Total number of pages (country: %s, currency: %s): %d. " +
      "Page %d by %d items is empty.";
  private static final String NO_INTERNAL_GAME_ID_IN_DB = "Internal game id \"%s\" not found in lobby database.";
  private static final String RESPONSE_GAME_REPO_NOT_SECURE = "Response from Game Repo is not secure.";
  private static final String RESPONSE_FROM_PLAYER_SERVICE_SUCCESS =
      "Player details from Player Service received successfully.";
  private static final String RESPONSE_FROM_GAME_REPO_SUCCESS = "Response from Game Repo received successfully.";
  static final Logger log = LoggerFactory.getLogger(LobbyGameServiceImpl.class);

  private final LobbyGameRepository lobbyGameRepository;
  private final PlayerDetailsCommunicationService playerDetailsCommunicationService;
  private final GameRepoCommunicationService gameRepoCommunicationService;
  private final ModelTransformer<LobbyGame, LobbyGameDto> lobbyGameModelTransformer;
  private final ModelTransformer<LaunchUrlFromGameRepo, LaunchUrlDto> launchUrlModelTransformer;
  @Value("${secret-salt}")
  private String secretSalt;

  public LobbyGameServiceImpl(LobbyGameRepository lobbyGameRepository,
                              PlayerDetailsCommunicationService playerDetailsCommunicationService,
                              GameRepoCommunicationService gameRepoCommunicationService,
                              ModelTransformer<LobbyGame, LobbyGameDto> lobbyGameModelTransformer,
                              ModelTransformer<LaunchUrlFromGameRepo, LaunchUrlDto> launchUrlModelTransformer) {
    this.lobbyGameRepository = lobbyGameRepository;
    this.playerDetailsCommunicationService = playerDetailsCommunicationService;
    this.gameRepoCommunicationService = gameRepoCommunicationService;
    this.lobbyGameModelTransformer = lobbyGameModelTransformer;
    this.launchUrlModelTransformer = launchUrlModelTransformer;
  }

  @Override
  public List<LobbyGameDto> getListOfAvailableGames(int page, int size, String sessionKey) {
    PlayerDetails detailsFromPlayerServiceBySessionKey =
        playerDetailsCommunicationService.getPlayerDetailsFromPlayerServiceBySessionKey(sessionKey);
    log.info(RESPONSE_FROM_PLAYER_SERVICE_SUCCESS);
    String playerCountryName = detailsFromPlayerServiceBySessionKey.getCountryName();
    String playerCurrencyName = detailsFromPlayerServiceBySessionKey.getCurrencyName();
    Pageable pageRequest = PageRequest.of(page, size, Sort.by("internal_game_id"));
    Page<LobbyGame> resultPageGameEntities =
        lobbyGameRepository.findAllByCountryNameAndCurrencyName(playerCountryName, playerCurrencyName, pageRequest);
    if (page >= resultPageGameEntities.getTotalPages()) {
      throw new EmptyPageException(String.format(EMPTY_PAGE_MESSAGE,
          playerCountryName, playerCurrencyName, resultPageGameEntities.getTotalPages(), page, size));
    }
    return resultPageGameEntities.getContent()
        .stream()
        .map(lobbyGameModelTransformer::asDto)
        .collect(Collectors.toList());
  }

  @Override
  public LaunchUrlDto getLaunchURLByLobbyGameIdAndSessionKey(String lobbyGameId, String sessionKey) {
    checkLobbyGameIDInDB(lobbyGameId);
    String hashToSendToGameRepo = generateHash(
        generateStringForHashToGameRepo(lobbyGameId, sessionKey));
    LaunchUrlFromGameRepo launchUrlFromGameRepo =
        gameRepoCommunicationService.getLaunchURLByLobbyGameIdAndSessionKey(
            lobbyGameId, sessionKey, hashToSendToGameRepo);
    log.info(RESPONSE_FROM_GAME_REPO_SUCCESS);
    String hashToCheckGameRepoResponse = generateHash(
        generateStringToCheckHashFromGameRepo(launchUrlFromGameRepo));
    if (!Objects.equals(hashToCheckGameRepoResponse, launchUrlFromGameRepo.getHash())) {
      throw new SecurityException(RESPONSE_GAME_REPO_NOT_SECURE);
    }
    return launchUrlModelTransformer.asDto(launchUrlFromGameRepo);
  }

  private void checkLobbyGameIDInDB(String lobbyGameId) {
    Optional<LobbyGame> lobbyGameCheckingInDB = lobbyGameRepository.findByInternalGameId(lobbyGameId);
    if (lobbyGameCheckingInDB.isEmpty()) {
      throw new NoInternalGameIdInDBException(String.format(NO_INTERNAL_GAME_ID_IN_DB, lobbyGameId));
    }
  }

  private String generateStringForHashToGameRepo(String lobbyGameId, String sessionKey) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(lobbyGameId);
    stringBuilder.append(sessionKey);
    stringBuilder.append(secretSalt);
    return stringBuilder.toString();
  }

  private String generateStringToCheckHashFromGameRepo(LaunchUrlFromGameRepo launchUrlFromGameRepo) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(launchUrlFromGameRepo.getCurrencyName());
    stringBuilder.append(launchUrlFromGameRepo.getDisplayName());
    stringBuilder.append(launchUrlFromGameRepo.getGameName());
    stringBuilder.append(launchUrlFromGameRepo.getLaunchProviderUrl());
    stringBuilder.append(launchUrlFromGameRepo.getSessionKey());
    stringBuilder.append(secretSalt);
    return stringBuilder.toString();
  }

  private String generateHash(String dataStringToHash) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] result = messageDigest.digest(dataStringToHash.getBytes(StandardCharsets.UTF_8));
      return DatatypeConverter.printHexBinary(result).toLowerCase(Locale.ROOT);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new GenerateHashException(noSuchAlgorithmException.getMessage());
    }
  }


}
