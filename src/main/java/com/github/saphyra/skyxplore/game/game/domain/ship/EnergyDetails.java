package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class EnergyDetails {
    private final int capacity;
    private volatile int actual;
    private final int recharge;

    @Builder
    public EnergyDetails(int capacity, int recharge) {
        this.capacity = capacity;
        this.actual = capacity;
        this.recharge = recharge;
    }
}
