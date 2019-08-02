package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.ship.GameShipDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class GameShipDetailsFactory {
    private final AbilityDetailsFactory abilityDetailsFactory;
    private final CoreHullDetailsFactory coreHullDetailsFactory;
    private final DefenseDetailsFactory defenseDetailsFactory;
    private final EnergyDetailsFactory energyDetailsFactory;
    private final StorageDetailsFactory storageDetailsFactory;
    private final WeaponDetailsFactory weaponDetailsFactory;

    GameShipDetails create(ShipEquipments equipments) {
        GameShipDetails gameShipDetails = GameShipDetails.builder()
            .coreHull(coreHullDetailsFactory.create(equipments))
            .defenseDetails(defenseDetailsFactory.create(equipments))
            .weaponDetails(weaponDetailsFactory.create(equipments))
            .energyDetails(energyDetailsFactory.create(equipments))
            .storageDetails(storageDetailsFactory.create(equipments))
            .abilityDetails(abilityDetailsFactory.create(equipments))
            .build();
        log.debug("Created GameShipDetails: {}", gameShipDetails);
        return gameShipDetails;
    }
}
