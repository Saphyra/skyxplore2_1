package com.github.saphyra.skyxplore.lobby.creation;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.LobbyContext;
import com.github.saphyra.skyxplore.lobby.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class LobbyObjectFactory {
    private final IdGenerator idGenerator;
    private final LobbyContext lobbyContext;

    public Lobby create(GameMode gameMode, String characterId, String data, int teamSize){
        FixedSizeConcurrentList<String> users = new FixedSizeConcurrentList<>(teamSize);
        users.add(characterId);

        return Lobby.builder()
            .lobbyId(idGenerator.randomUUID())
            .gameMode(gameMode)
            .users(users)
            .data(data)
            .ownerId(characterId)
            .lobbyContext(lobbyContext)
            .build();
    }
}
