package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.ClanWarsType;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ClanWarsLobbyFactory implements LobbyFactory {
    private final LobbyCreationConfiguration configuration;
    private final LobbyObjectFactory lobbyObjectFactory;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.CLAN_WARS;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        validateType(data);
        return lobbyObjectFactory.create(GameMode.CLAN_WARS, characterId, data, configuration.getClanWarsLobbySize());
    }

    private void validateType(String data) {
        if (!Optional.ofNullable(ClanWarsType.fromValue(data)).isPresent()) {
            throw new BadRequestException("ClanWarsType " + data + " is not allowed.");
        }
    }
}
