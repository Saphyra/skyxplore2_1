package com.github.saphyra.skyxplore.game.game.domain;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
//TODO unit test
public class Team {
    private final UUID teamId;
    private final TeamColor teamColor;

    @Builder.Default
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

    public boolean containsCharacter(String characterId) {
        return characters.stream()
            .anyMatch(gameCharacter -> gameCharacter.getCharacterId().equals(characterId));
    }
}
