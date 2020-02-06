package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Vector {
    private final Coordinates startPoint;
    private final Coordinates endPoint;
}
