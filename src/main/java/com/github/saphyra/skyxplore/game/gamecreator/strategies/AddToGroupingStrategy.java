package com.github.saphyra.skyxplore.game.gamecreator.strategies;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

public interface AddToGroupingStrategy {
    boolean canAdd(GameMode gameMode);

    void add(Lobby lobby);
}
