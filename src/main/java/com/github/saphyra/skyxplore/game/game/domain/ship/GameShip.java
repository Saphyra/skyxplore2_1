package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class GameShip {
    private final UUID gameShipId;

    protected volatile boolean isAlive = true;

    private final Coordinates position;

    private final GameShipDetails gameShipDetails;

    @Builder
    public GameShip(
        @NonNull UUID gameShipId,
        @NonNull Coordinates position,
        @NonNull GameShipDetails gameShipDetails
    ) {
        this.gameShipId = gameShipId;
        this.position = position;
        this.gameShipDetails = gameShipDetails;
    }
}
