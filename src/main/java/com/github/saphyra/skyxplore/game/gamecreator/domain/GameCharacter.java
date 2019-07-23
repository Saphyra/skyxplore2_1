package com.github.saphyra.skyxplore.game.gamecreator.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GameCharacter {
    @NonNull
    private final String  characterId;

    @Builder.Default
    private final boolean isAi = false;
}
