package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ShipEquipments {
    private final String shipId;

    @NonNull
    @Builder.Default
    private final List<String> connectorEquipped = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> frontDefense = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> leftDefense = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> rightDefense = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> backDefense = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> frontWeapon = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> leftWeapon = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> rightWeapon = new ArrayList<>();

    @NonNull
    @Builder.Default
    private final List<String> backWeapon = new ArrayList<>();
}
