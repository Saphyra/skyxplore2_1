package com.github.saphyra.skyxplore.game.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Coordinates {
    private int xCoord;
    private int yCoord;
}
