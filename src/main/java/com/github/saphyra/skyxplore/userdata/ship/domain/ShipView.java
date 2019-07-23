package com.github.saphyra.skyxplore.userdata.ship.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ShipView {
    @NonNull
    private final String shipType;

    @NonNull
    private final Integer coreHull;

    @NonNull
    private final Integer connectorSlot;

    @NonNull
    private final List<String> connectorEquipped;

    @NonNull
    private final SlotView defenseSlot;

    @NonNull
    private final SlotView weaponSlot;

    @NonNull
    private final List<String> ability;
}
