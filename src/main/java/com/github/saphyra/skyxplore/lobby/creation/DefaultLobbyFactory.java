package com.github.saphyra.skyxplore.lobby.creation;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class DefaultLobbyFactory implements LobbyFactory {
    private final LobbyObjectFactory lobbyObjectFactory;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.ARCADE
            || gameMode == GameMode.BATTLE_ROYALE
            || gameMode == GameMode.VS
            || gameMode == GameMode.TOURNAMENT;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        return lobbyObjectFactory.create(gameMode, characterId, data, Integer.MAX_VALUE);
    }
}
