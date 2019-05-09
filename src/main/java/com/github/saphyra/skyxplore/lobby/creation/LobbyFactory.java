package com.github.saphyra.skyxplore.lobby.creation;

import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;

interface LobbyFactory {
    boolean canCreate(GameMode gameMode);

    Lobby create(GameMode gameMode, String characterId, String data);
}