package com.github.saphyra.skyxplore.lobby.lobby.creation;

import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultLobbyFactory implements LobbyFactory {
    static final int MAX_LOBBY_SIZE = Integer.MAX_VALUE; //TODO set

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
        return lobbyObjectFactory.create(gameMode, characterId, data, MAX_LOBBY_SIZE);
    }
}
