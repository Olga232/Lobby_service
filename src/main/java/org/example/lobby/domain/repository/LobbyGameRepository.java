package org.example.lobby.domain.repository;

import org.example.lobby.domain.models.repositoryEntity.LobbyGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LobbyGameRepository extends JpaRepository<LobbyGame, Integer> {

  @Query(value = "SELECT * FROM lobby_game l " +
      "JOIN country c ON c.name = :playerCountryName AND c.id = l.country_id " +
      "JOIN currency cur ON cur.name = :playerCurrencyName AND cur.id = l.currency_id",
      nativeQuery = true)
  Page<LobbyGame> findAllByCountryNameAndCurrencyName(
      String playerCountryName, String playerCurrencyName, Pageable pageRequest);

  Optional<LobbyGame> findByInternalGameId(String internalGameId);

}
