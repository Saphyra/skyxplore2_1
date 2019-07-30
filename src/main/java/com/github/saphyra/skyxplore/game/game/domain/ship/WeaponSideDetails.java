package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class WeaponSideDetails {
    @NonNull
    private final String itemId;
    private final int attackSpeed;
    private final int range;
    private final int criticalRate;
    private final int hullDamage;
    private final int shieldDamage;
    private final int accuracy;
}
