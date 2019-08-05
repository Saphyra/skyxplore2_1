package com.github.saphyra.skyxplore.game.game.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.common.domain.message.Message;
import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(exclude = "gameContext")
@EqualsAndHashCode
public class Game {
    @Getter
    private final UUID gameId;

    @Getter
    private final GameMode gameMode;

    @Getter
    private final Object data;

    private final GameContext gameContext;

    private final List<Team> teams = new Vector<>();
    private final List<GameShip> ships = new Vector<>();
    private final List<Message> messages = new Vector<>();

    @Builder
    private Game(
        @NonNull UUID gameId,
        @NonNull GameMode gameMode,
        Object data,
        @NonNull GameContext gameContext
    ) {
        this.gameId = gameId;
        this.gameMode = gameMode;
        this.data = data;
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

    public void addShips(@NonNull List<GameShip> ships) {
        this.ships.addAll(ships);
    }

    public void addShip(@NonNull GameShip ship) {
        this.ships.add(ship);
    }

    public List<GameShip> getShips(){
        return new ArrayList<>(ships);
    }
}
