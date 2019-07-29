package com.github.saphyra.skyxplore.game.game.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GameCharacter {
    @NonNull
    @Getter
    private final String characterId;

    private final String characterName;

    private final boolean originallyAi;

    private volatile boolean isAi = false;

    @Builder
    public GameCharacter(
        @NonNull String characterId,
        @NonNull String characterName,
        boolean originallyAi
    ) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.originallyAi = originallyAi;
    }
}
