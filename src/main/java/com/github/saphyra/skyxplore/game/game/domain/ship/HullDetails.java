package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class HullDetails {
    private final String itemId;

    private final int maxHull;

    private volatile int actualHull;

    @Builder
    public HullDetails(
        @NonNull String itemId,
        int maxHull
    ) {
        this.itemId = itemId;
        this.maxHull = maxHull;
        this.actualHull = maxHull;
    }
}
