package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ShieldDetails {
    private final String itemId;

    private final int maxShield;

    private volatile int actualShield;

    private final int recharge;

    private final int energyUsage;

    @Builder
    public ShieldDetails(
        @NonNull String itemId,
        int maxShield,
        int recharge,
        int energyUsage
    ) {
        this.itemId = itemId;
        this.maxShield = maxShield;
        this.actualShield = maxShield;
        this.recharge = recharge;
        this.energyUsage = energyUsage;
    }
}
