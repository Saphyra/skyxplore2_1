package com.github.saphyra.skyxplore.lobby.creation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;

@Component
class DefaultLobbyFactory implements LobbyFactory {
    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.ARCADE
            || gameMode == GameMode.BATTLE_ROYALE
            || gameMode == GameMode.VS
            || gameMode == GameMode.TOURNAMENT;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        FixedSizeConcurrentList<String> users = new FixedSizeConcurrentList<>(Integer.MAX_VALUE);
        users.add(characterId);
        return Lobby.builder()
            //TODO use idgenerator
            .lobbyId(UUID.randomUUID())
            .gameMode(gameMode)
            .users(users)
            .data(data)
            .build();
    }
}
