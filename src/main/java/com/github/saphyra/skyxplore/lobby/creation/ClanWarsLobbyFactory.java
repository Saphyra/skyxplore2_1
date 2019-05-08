package com.github.saphyra.skyxplore.lobby.creation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.domain.ClanWarsType;
import com.github.saphyra.skyxplore.lobby.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class ClanWarsLobbyFactory implements LobbyFactory {
    private final IdGenerator idGenerator;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.CLAN_WARS;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        FixedSizeConcurrentList<String> users = new FixedSizeConcurrentList<>(Integer.MAX_VALUE);
        users.add(characterId);

        validateType(data);

        return Lobby.builder()
            .lobbyId(idGenerator.randomUUID())
            .gameMode(gameMode)
            .users(users)
            .data(data)
            .build();
    }

    private void validateType(String data) {
        if (!Optional.ofNullable(ClanWarsType.fromValue(data)).isPresent()) {
            throw new BadRequestException("ClanWarsType " + data + " is not allowed.");
        }
    }
}
