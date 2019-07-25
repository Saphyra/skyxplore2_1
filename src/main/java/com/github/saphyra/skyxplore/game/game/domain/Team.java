package com.github.saphyra.skyxplore.game.game.domain;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
//TODO unit test
public class Team {
    @NonNull
    private final UUID teamId;

    @NonNull
    @Builder.Default
    private final List<GameCharacter> characters = new Vector<>();

    public boolean containsCharacter(String characterId) {
        return characters.stream()
            .anyMatch(gameCharacter -> gameCharacter.getCharacterId().equals(characterId));
    }
}
