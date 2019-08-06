package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

@Component
public class GameGroupSizeRangeProvider {
    public GameGroupSizeRange getGameModeSizeRange(GameMode gameMode) {
        return getGameModeSizeRange(gameMode, null);
    }

    public GameGroupSizeRange getGameModeSizeRange(GameMode gameMode, Object data) {
        switch (gameMode) {
            case VS:
                return new GameGroupSizeRange(1, 1);
            default:
                throw new RuntimeException("No GameModeSizeRange available for gameMode " + gameMode);
        }
    }
}
