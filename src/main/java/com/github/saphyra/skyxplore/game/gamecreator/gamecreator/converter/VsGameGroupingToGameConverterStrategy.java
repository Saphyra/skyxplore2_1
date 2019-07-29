package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.converter;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.GameFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class VsGameGroupingToGameConverterStrategy implements GameGroupingToGameConverterStrategy {
    private final GameFactory gameFactory;

    @Override
    public boolean canConvert(GameMode gameMode) {
        return gameMode == GameMode.VS;
    }

    @Override
    public Game convert(GameGrouping gameGrouping) {
        return gameFactory.create(gameGrouping);
    }
}
