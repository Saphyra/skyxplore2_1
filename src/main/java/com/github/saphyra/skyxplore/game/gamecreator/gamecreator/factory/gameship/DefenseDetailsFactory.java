package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.ship.DefenseDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class DefenseDetailsFactory {
    private final DefenseSideDetailsFactory defenseSideDetailsFactory;

    public DefenseDetails create(ShipEquipments equipments) {
        DefenseDetails defenseDetails = DefenseDetails.builder()
            .frontDefense(defenseSideDetailsFactory.create(equipments.getFrontDefense()))
            .leftDefense(defenseSideDetailsFactory.create(equipments.getLeftDefense()))
            .rightDefense(defenseSideDetailsFactory.create(equipments.getRightDefense()))
            .backDefense(defenseSideDetailsFactory.create(equipments.getBackDefense()))
            .build();
        log.debug("Created DefenseDetails: {}", defenseDetails);
        return defenseDetails;
    }
}
