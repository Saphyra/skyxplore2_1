package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DefaultLobbyFactory implements LobbyFactory {
    private final LobbyCreationConfiguration configuration;
    private final LobbyObjectFactory lobbyObjectFactory;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.ARCADE
            || gameMode == GameMode.BATTLE_ROYALE;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        if (!canCreate(gameMode)) {
            throw new IllegalArgumentException("DefaultLobby can not be created for gameMode " + gameMode);
        }
        return lobbyObjectFactory.create(gameMode, characterId, data, configuration.getDefaultLobbySize());
    }
}
