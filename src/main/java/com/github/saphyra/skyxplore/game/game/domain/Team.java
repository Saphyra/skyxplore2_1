package com.github.saphyra.skyxplore.game.game.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

@ToString
@EqualsAndHashCode
public class Team {
    private final UUID teamId;
    private final TeamColor teamColor;

    private final List<GameCharacter> characters = new Vector<>();

    @Builder
    public Team(
        @NonNull UUID teamId,
        @NonNull TeamColor teamColor
    ) {
        this.teamId = teamId;
        this.teamColor = teamColor;
    }

    public Team addCharacters(@NonNull List<GameCharacter> characters) {
        this.characters.addAll(characters);
        return this;
    }

    boolean containsCharacter(String characterId) {
        return characters.stream()
            .anyMatch(gameCharacter -> gameCharacter.getCharacterId().equals(characterId));
    }
}
