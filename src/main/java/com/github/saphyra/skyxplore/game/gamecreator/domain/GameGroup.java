package com.github.saphyra.skyxplore.game.gamecreator.domain;

import java.util.List;
import java.util.UUID;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
public class GameGroup {
    @NonNull
    private final UUID gameGroupId;

    @NonNull
    private final List<GameCharacter> characters;

    private final boolean autoFill;

    @Builder
    private GameGroup(@NonNull UUID gameGroupId, int maxGroupSize, boolean autoFill) {
        this.gameGroupId = gameGroupId;
        this.characters = new FixedSizeConcurrentList<>(maxGroupSize);
        this.autoFill = autoFill;
    }

    public GameGroup addCharacter(GameCharacter gameCharacter) {
        characters.add(gameCharacter);
        return this;
    }
}
