package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Coordinates {
    public static final int DEFAULT_COORDINATES = -1;

    @Builder.Default
    private volatile int xCoord = -1;

    @Builder.Default
    private volatile int yCoord = -1;

    public static Coordinates createDefault() {
        return new Coordinates(DEFAULT_COORDINATES, DEFAULT_COORDINATES);
    }
}
