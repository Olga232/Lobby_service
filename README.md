# Lobby_service
The gaming platform is consists of three services: Player Service, Lobby, and Games Repository. Lobby service starts a generation of launch URLs on the gaming platform.

Lobby fetches detailed information from the Player Service about the player using the provided session, and based on this info Lobby validates restrictions for the player. Lobby persists the list of lobby game ids and conditions where each game is allowed and which currency is supported. The list of game ids is paginated. The player can get available games and launch the game by request.
Lobby accepts session and lobby game id from the client service, asks Games Repository for launch URL, and returns it in response.

Stack: Spring Boot, JPA, PostgreSQL, Flyway, Docker.
