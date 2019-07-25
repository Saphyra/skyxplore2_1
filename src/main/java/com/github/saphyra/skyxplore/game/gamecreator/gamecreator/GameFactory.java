package com.github.saphyra.skyxplore.game.gamecreator.gamecreator;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameFactory {
    private final IdGenerator idGenerator;

    public Game create() {
        Game game = Game.builder()
            .gameId(idGenerator.randomUUID())
            .build();
        log.debug("Game created: {}", game);
        return game;
    }
}
