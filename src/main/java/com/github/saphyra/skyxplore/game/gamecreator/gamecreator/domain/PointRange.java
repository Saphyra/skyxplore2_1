package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PointRange {
    private final int minPoints;
    private final int maxPoints;
}
