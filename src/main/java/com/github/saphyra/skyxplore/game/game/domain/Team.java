package com.github.saphyra.skyxplore.game.game.domain;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

    String getNameOfCharacter(String characterId) {
        return characters.stream()
            .filter(gameCharacter -> gameCharacter.getCharacterId().equals(characterId))
            .findFirst()
            .map(GameCharacter::getCharacterName)
            .orElseThrow(() -> ExceptionFactory.characterNotInTeam(characterId, teamId));
    }
}
