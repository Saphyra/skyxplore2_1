package com.github.saphyra.skyxplore.game.game.domain;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.common.domain.message.Message;
import com.github.saphyra.skyxplore.game.game.GameContext;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
//TODO unit test
public class Game {
    @Getter
    private final UUID gameId;

    private final GameContext gameContext;

    private final List<Team> teams = new Vector<>();
    private final List<GameShip> ships = new Vector<>();
    private final List<Message> messages = new Vector<>();

    @Builder
    private Game(
        @NonNull UUID gameId,
        @NonNull GameContext gameContext
    ) {
        this.gameId = gameId;
        this.gameContext = gameContext;
    }

    public boolean containsCharacter(String characterId) {
        return teams.stream()
            .anyMatch(team -> team.containsCharacter(characterId));
    }

    public Game addTeams(@NonNull List<Team> teams) {
        this.teams.addAll(teams);
        return this;
    }

    public Game addShips(@NonNull List<GameShip> ships) {
        this.ships.addAll(ships);
        return this;
    }
}
