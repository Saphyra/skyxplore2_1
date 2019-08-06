package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class LobbyCreationConfiguration {
    @Value("${lobby.creation.tournament.size}")
    private Integer tournamentLobbySize;

    @Value("${lobby.creation.clanwars.size}")
    private Integer clanWarsLobbySize;

    @Value("${lobby.creation.default.size}")
    private Integer defaultLobbySize;
}
