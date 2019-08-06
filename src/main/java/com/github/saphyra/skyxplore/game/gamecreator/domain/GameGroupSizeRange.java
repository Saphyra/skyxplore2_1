package com.github.saphyra.skyxplore.game.gamecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class GameGroupSizeRange {
    private final int minGroupSize;
    private final int maxGroupSize;
}
