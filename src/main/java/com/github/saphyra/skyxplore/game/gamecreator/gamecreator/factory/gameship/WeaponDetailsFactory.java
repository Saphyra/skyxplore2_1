package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.game.domain.ship.WeaponDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeaponDetailsFactory {
    private final WeaponSideDetailsFactory weaponSideDetailsFactory;

    public WeaponDetails create(ShipEquipments equipments) {
        WeaponDetails weaponDetails = WeaponDetails.builder()
            .frontWeapon(weaponSideDetailsFactory.create(equipments.getFrontWeapon()))
            .leftWeapon(weaponSideDetailsFactory.create(equipments.getLeftWeapon()))
            .rightWeapon(weaponSideDetailsFactory.create(equipments.getRightWeapon()))
            .backWeapon(weaponSideDetailsFactory.create(equipments.getBackWeapon()))
            .build();
        log.debug("Created WeaponDetails: {}", weaponDetails);
        return weaponDetails;
    }
}
