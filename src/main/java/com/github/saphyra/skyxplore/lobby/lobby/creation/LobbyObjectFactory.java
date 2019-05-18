package com.github.saphyra.skyxplore.lobby.lobby.creation;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyContext;
import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO unit test
public class LobbyObjectFactory {
    private final IdGenerator idGenerator;
    private final LobbyContext lobbyContext;

    public Lobby create(GameMode gameMode, String characterId, String data, int teamSize) {
        FixedSizeConcurrentList<LobbyMember> members = new FixedSizeConcurrentList<>(teamSize);
        members.add(LobbyMember.builder().characterId(characterId).build());

        return Lobby.builder()
            .lobbyId(idGenerator.randomUUID())
            .gameMode(gameMode)
            .members(members)
            .data(data)
            .ownerId(characterId)
            .lobbyContext(lobbyContext)
            .build();
    }
}
