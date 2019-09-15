package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import com.github.saphyra.skyxplore.data.gamedata.entity.Ability;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.AbilityService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.game.game.domain.ship.AbilityDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AbilityDetailsFactory {
    private final AbilityService abilityService;
    private final ShipService shipService;

    public List<AbilityDetails> create(ShipEquipments equipments) {
        List<AbilityDetails> abilityDetails = fetchAbilityDetails(equipments.getShipId());
        log.debug("Created AbilityDetails: {}", abilityDetails);
        return abilityDetails;
    }

    private List<AbilityDetails> fetchAbilityDetails(String shipId) {
        return getShip(shipId)
            .getAbility()
            .stream()
            .map(this::getAbility)
            .map(this::getAbilityDetails)
            .collect(Collectors.toList());
    }

    private Ship getShip(String shipId) {
        return shipService.getOptional(shipId)
            .orElseThrow(() -> new RuntimeException("No Ship found with shipId " + shipId));
    }

    private Ability getAbility(String abilityId) {
        return abilityService.getOptional(abilityId)
            .orElseThrow(() -> new RuntimeException("Ability not found."));
    }

    private AbilityDetails getAbilityDetails(Ability ability) {
        return AbilityDetails.builder()
            .itemId(ability.getId())
            .energyUsage(ability.getEnergyUsage())
            .reload(ability.getReload())
            .active(ability.getActive())
            .data(Optional.ofNullable(ability.getEffect()).orElse(new HashMap<>()))
            .build();
    }
}
