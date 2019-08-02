package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.entity.Shield;
import com.github.saphyra.skyxplore.data.subservice.ShieldService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShieldDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ShieldDetailsFactory {
    private final ShieldService shieldService;

    public List<ShieldDetails> create(List<String> items) {
        List<ShieldDetails> shieldDetails = items.stream()
            .filter(itemId -> !isNull(shieldService.get(itemId)))
            .map(this::createShieldDetails)
            .collect(Collectors.toList());
        log.debug("Created ShieldDetails: {}", shieldDetails);
        return shieldDetails;
    }

    private ShieldDetails createShieldDetails(String shieldId) {
        Shield shield = shieldService.get(shieldId);
        return ShieldDetails.builder()
            .itemId(shieldId)
            .maxShield(shield.getCapacity())
            .recharge(shield.getRegeneration())
            .energyUsage(shield.getEnergyUsage())
            .build();
    }
}
