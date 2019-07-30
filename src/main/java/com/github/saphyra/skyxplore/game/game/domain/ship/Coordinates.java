package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Coordinates {
    private volatile int xCoord;
    private volatile int yCoord;
}
