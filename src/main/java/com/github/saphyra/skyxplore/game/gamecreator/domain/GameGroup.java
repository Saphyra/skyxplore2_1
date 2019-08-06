package com.github.saphyra.skyxplore.game.gamecreator.domain;

import java.util.UUID;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class GameGroup {
    @NonNull
    @Getter
    private final UUID gameGroupId;

    @NonNull
    private final FixedSizeConcurrentList<GameGroupCharacter> characters;

    @Getter
    private final boolean autoFill;

    @Builder
    private GameGroup(@NonNull UUID gameGroupId, int maxGroupSize, boolean autoFill) {
        this.gameGroupId = gameGroupId;
        this.characters = new FixedSizeConcurrentList<>(maxGroupSize);
        this.autoFill = autoFill;
    }

    public GameGroup addCharacter(GameGroupCharacter gameGroupCharacter) {
        characters.add(gameGroupCharacter);
        return this;
    }

    public FixedSizeConcurrentList<GameGroupCharacter> getCharacters() {
        return new FixedSizeConcurrentList<>(characters.getMaxSize(), characters);
    }
}
