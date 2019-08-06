package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class GameShip {
    private final UUID gameShipId;

    protected volatile boolean isAlive = true;

    private final Coordinates position;

    private final GameShipDetails gameShipDetails;

    @Getter
    private final ShipEquipments shipEquipments;

    @Builder
    public GameShip(
        @NonNull UUID gameShipId,
        @NonNull Coordinates position,
        @NonNull GameShipDetails gameShipDetails,
        @NonNull ShipEquipments shipEquipments
    ) {
        this.gameShipId = gameShipId;
        this.position = position;
        this.gameShipDetails = gameShipDetails;
        this.shipEquipments = shipEquipments;
    }
}
