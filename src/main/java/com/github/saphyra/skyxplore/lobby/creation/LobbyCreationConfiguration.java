package com.github.saphyra.skyxplore.lobby.creation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class LobbyCreationConfiguration {
    @Value("${lobby.creation.tournament.size}")
    private Integer tournamentLobbySize;
}
