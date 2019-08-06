package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
@Getter
public class WeaponDetails {
    @NonNull
    private final List<WeaponSideDetails> frontWeapon;

    @NonNull
    private final List<WeaponSideDetails> leftWeapon;

    @NonNull
    private final List<WeaponSideDetails> rightWeapon;

    @NonNull
    private final List<WeaponSideDetails> backWeapon;
}
