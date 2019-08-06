package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Getter
public class GameShipDetails {
    @NonNull
    private final HullDetails coreHull;

    @NonNull
    private final DefenseDetails defenseDetails;

    @NonNull
    private final WeaponDetails weaponDetails;

    @NonNull
    private final EnergyDetails energyDetails;

    @NonNull
    private final StorageDetails storageDetails;

    @NonNull
    private final List<AbilityDetails> abilityDetails;

    public List<AbilityDetails> getAbilityDetails(){
        return new ArrayList<>(abilityDetails);
    }
}
