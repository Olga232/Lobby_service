CREATE TABLE country
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                             NOT NULL,
    code VARCHAR(255)                             NOT NULL,
    CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE currency
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                             NOT NULL,
    code VARCHAR(255)                             NOT NULL,
    CONSTRAINT pk_currency PRIMARY KEY (id)
);

CREATE TABLE lobby_game
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    internal_game_id VARCHAR(255)                             NOT NULL,
    country_id       INTEGER,
    currency_id      INTEGER,
    CONSTRAINT pk_lobby_game PRIMARY KEY (id)
);

ALTER TABLE country
    ADD CONSTRAINT uc_country_code UNIQUE (code);

ALTER TABLE country
    ADD CONSTRAINT uc_country_name UNIQUE (name);

ALTER TABLE currency
    ADD CONSTRAINT uc_currency_code UNIQUE (code);

ALTER TABLE currency
    ADD CONSTRAINT uc_currency_name UNIQUE (name);

ALTER TABLE lobby_game
    ADD CONSTRAINT uc_lobby_game_internal_game UNIQUE (internal_game_id);

ALTER TABLE lobby_game
    ADD CONSTRAINT FK_LOBBY_GAME_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE lobby_game
    ADD CONSTRAINT FK_LOBBY_GAME_ON_CURRENCY FOREIGN KEY (currency_id) REFERENCES currency (id);