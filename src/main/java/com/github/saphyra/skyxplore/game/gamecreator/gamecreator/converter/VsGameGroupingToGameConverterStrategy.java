package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.converter;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessages;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.GameFactory;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.GameMessageFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

@Component
public class VsGameGroupingToGameConverterStrategy extends AbstractGameGroupingToGameConverter {
    private final GameFactory gameFactory;

    public VsGameGroupingToGameConverterStrategy(
        GameMessageFactory gameMessageFactory,
        GameFactory gameFactory
    ) {
        super(gameMessageFactory);
        this.gameFactory = gameFactory;
    }

    @Override
    public boolean canConvert(GameMode gameMode) {
        return gameMode == GameMode.VS;
    }

    @Override
    protected Game createGame(GameGrouping gameGrouping, GameMessages gameMessages) {
        return gameFactory.create(gameGrouping, gameMessages);
    }
}
