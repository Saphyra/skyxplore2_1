package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VsLobbyFactory implements LobbyFactory {
    private final LobbyObjectFactory lobbyObjectFactory;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.VS;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        return lobbyObjectFactory.create(GameMode.VS, characterId, data, 2);
    }
}
