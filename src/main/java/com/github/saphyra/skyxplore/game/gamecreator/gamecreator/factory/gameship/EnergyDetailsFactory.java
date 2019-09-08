package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.gamedata.entity.Battery;
import com.github.saphyra.skyxplore.data.gamedata.entity.Generator;
import com.github.saphyra.skyxplore.data.gamedata.subservice.BatteryService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.GeneratorService;
import com.github.saphyra.skyxplore.game.game.domain.ship.EnergyDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnergyDetailsFactory {
    private final BatteryService batteryService;
    private final GeneratorService generatorService;

    public EnergyDetails create(ShipEquipments equipments) {
        int capacity = fetchCapacity(equipments.getConnectorEquipped());
        int recharge = fetchRecharge(equipments.getConnectorEquipped());
        EnergyDetails energyDetails = EnergyDetails.builder()
            .capacity(capacity)
            .recharge(recharge)
            .build();
        log.debug("Created EnergyDetails: {}", energyDetails);
        return energyDetails;
    }

    private int fetchCapacity(List<String> items) {
        return items.stream()
            .filter(itemId -> !isNull(batteryService.get(itemId)))
            .map(batteryService::get)
            .mapToInt(Battery::getCapacity)
            .sum();
    }

    private int fetchRecharge(List<String> items) {
        return items.stream()
            .filter(itemId -> !isNull(generatorService.get(itemId)))
            .map(generatorService::get)
            .mapToInt(Generator::getEnergyRecharge)
            .sum();
    }
}
