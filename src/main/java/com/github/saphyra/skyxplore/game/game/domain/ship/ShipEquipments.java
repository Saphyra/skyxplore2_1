package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ShipEquipments {
    private final String shipId;
    private final List<String> connectorEquipped;

    private final List<String> frontDefense;
    private final List<String> leftDefense;
    private final List<String> rightDefense;
    private final List<String> backDefense;

    private final List<String> frontWeapon;
    private final List<String> leftWeapon;
    private final List<String> rightWeapon;
    private final List<String> backWeapon;
}
