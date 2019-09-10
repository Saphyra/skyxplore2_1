package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.Team;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessages;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.GameShipFactory;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameFactory {
    private final GameContext gameContext;
    private final GameShipFactory gameShipFactory;
    private final IdGenerator idGenerator;
    private final TeamFactory teamFactory;

    public Game create(GameGrouping gameGrouping, GameMessages gameMessages) {
        List<Team> teams = teamFactory.create(gameGrouping);
        Game game = Game.builder()
            .gameId(idGenerator.randomUUID())
            .gameMode(gameGrouping.getGameMode())
            .data(gameGrouping.getData())
            .gameContext(gameContext)
            .messages(gameMessages)
            .build()
            .addTeams(teams);
        game.addShips(createPlayerShips(gameGrouping, game));
        createAiShips(gameGrouping, game);

        log.debug("Game created: {}", game);
        return game;
    }

    private List<GameShip> createPlayerShips(GameGrouping gameGrouping, Game game) {
        return getFilteredGameCharacters(gameGrouping, false).stream()
            .map(gameGroupCharacter -> gameShipFactory.create(gameGroupCharacter, game))
            .collect(Collectors.toList());
    }

    private void createAiShips(GameGrouping gameGrouping, Game game) {
        List<GameGroupCharacter> aiGameGroupCharacters = getFilteredGameCharacters(gameGrouping, true);
        for (GameGroupCharacter gameGroupCharacter : aiGameGroupCharacters) {
            game.addShip(gameShipFactory.create(gameGroupCharacter, game));
        }
    }

    private List<GameGroupCharacter> getFilteredGameCharacters(GameGrouping gameGrouping, boolean isAi) {
        return gameGrouping.getGameGroups().stream()
            .flatMap(gameGroup -> gameGroup.getCharacters().stream())
            .filter(gameGroupCharacter -> gameGroupCharacter.isAi() == isAi)
            .collect(Collectors.toList());
    }
}
