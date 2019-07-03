package com.github.saphyra.skyxplore.lobby.lobby.creation;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.ClanWarsType;
import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class ClanWarsLobbyFactory implements LobbyFactory {
     static final int MAX_LOBBY_SIZE = Integer.MAX_VALUE; //TODO set

    private final LobbyObjectFactory lobbyObjectFactory;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.CLAN_WARS;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        validateType(data);
        return lobbyObjectFactory.create(GameMode.CLAN_WARS, characterId, data, MAX_LOBBY_SIZE);
    }

    private void validateType(String data) {
        if (!Optional.ofNullable(ClanWarsType.fromValue(data)).isPresent()) {
            throw new BadRequestException("ClanWarsType " + data + " is not allowed.");
        }
    }
}
