package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.entity.Armor;
import com.github.saphyra.skyxplore.data.subservice.ArmorService;
import com.github.saphyra.skyxplore.game.game.domain.ship.HullDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HullDetailsFactory {
    private final ArmorService armorService;

    public List<HullDetails> create(List<String> items) {
        List<HullDetails> hullDetails = items.stream()
            .filter(itemId -> !isNull(armorService.get(itemId)))
            .map(this::createHullDetails)
            .collect(Collectors.toList());
        log.debug("Created HullDetails: {}", hullDetails);
        return hullDetails;
    }

    private HullDetails createHullDetails(String itemId) {
        Armor armor = armorService.get(itemId);
        return HullDetails.builder()
            .itemId(itemId)
            .maxHull(armor.getCapacity())
            .build();
    }
}
