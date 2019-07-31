package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class UpgradableItem {
    private final Optional<UpgradableSlot> upgradableSlot;
    private final Optional<String> upgradeableItemId;
}
