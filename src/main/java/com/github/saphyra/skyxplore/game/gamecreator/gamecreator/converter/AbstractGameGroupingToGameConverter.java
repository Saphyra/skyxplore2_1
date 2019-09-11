package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.converter;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessages;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.GameMessagesFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractGameGroupingToGameConverter implements GameGroupingToGameConverterStrategy {
    private final GameMessagesFactory gameMessagesFactory;

    @Override
    public Game convert(GameGrouping gameGrouping) {
        GameMessages gameMessages = gameMessagesFactory.create(gameGrouping);
        return createGame(gameGrouping, gameMessages);
    }

    protected abstract Game createGame(GameGrouping gameGrouping, GameMessages gameMessages);
}
