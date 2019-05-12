package com.github.saphyra.skyxplore.lobby.lobby.creation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.ClanWarsType;
import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class ClanWarsLobbyFactory implements LobbyFactory {
    private final LobbyObjectFactory lobbyObjectFactory;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.CLAN_WARS;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        validateType(data);
        return lobbyObjectFactory.create(gameMode, characterId, data, Integer.MAX_VALUE);
    }

    private void validateType(String data) {
        if (!Optional.ofNullable(ClanWarsType.fromValue(data)).isPresent()) {
            throw new BadRequestException("ClanWarsType " + data + " is not allowed.");
        }
    }
}
