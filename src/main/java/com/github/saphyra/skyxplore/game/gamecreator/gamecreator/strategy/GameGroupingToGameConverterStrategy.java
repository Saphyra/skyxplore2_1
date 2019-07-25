package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.strategy;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

public interface GameGroupingToGameConverterStrategy {
    boolean canConvert(GameMode gameMode);

    Game convert(GameGrouping gameGrouping);
}
