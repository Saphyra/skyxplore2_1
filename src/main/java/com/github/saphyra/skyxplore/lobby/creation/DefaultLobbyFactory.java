package com.github.saphyra.skyxplore.lobby.creation;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class DefaultLobbyFactory implements LobbyFactory {
    private final IdGenerator idGenerator;

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
            .lobbyId(idGenerator.randomUUID())
            .gameMode(gameMode)
            .users(users)
            .data(data)
            .ownerId(characterId)
            .build();
    }
}
