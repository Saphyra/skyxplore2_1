package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.Team;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameFactory {
    private final GameContext gameContext;
    private final IdGenerator idGenerator;
    private final TeamFactory teamFactory;

    public Game create(GameGrouping gameGrouping) {
        List<Team> teams = teamFactory.create(gameGrouping);
        Game game = Game.builder()
            .gameId(idGenerator.randomUUID())
            .gameMode(gameGrouping.getGameMode())
            .data(gameGrouping.getData())
            .gameContext(gameContext)
            .build()
            .addTeams(teams);
        log.debug("Game created: {}", game);
        return game;
    }
}
