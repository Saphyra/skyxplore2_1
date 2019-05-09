package com.github.saphyra.skyxplore.lobby.creation;

import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO unit test
public class TournamentLobbyFactory implements LobbyFactory {
    private final LobbyObjectFactory lobbyObjectFactory;
    private final LobbyCreationConfiguration configuration;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.TOURNAMENT;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        return lobbyObjectFactory.create(gameMode, characterId, data, configuration.getTournamentLobbySize());
    }
}
