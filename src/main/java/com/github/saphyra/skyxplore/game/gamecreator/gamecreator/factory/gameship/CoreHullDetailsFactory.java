package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import com.github.saphyra.skyxplore.data.entity.CoreHull;
import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.data.subservice.CoreHullService;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.skyxplore.game.game.domain.ship.HullDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class CoreHullDetailsFactory {
    private final CoreHullService coreHullService;
    private final ShipService shipService;

    public HullDetails create(ShipEquipments equipments) {
        String shipId = equipments.getShipId();

        int maxHull = fetchMaxHull(shipId, equipments.getConnectorEquipped());
        HullDetails hullDetails = HullDetails.builder()
            .itemId(shipId)
            .maxHull(maxHull)
            .build();
        log.debug("CoreHullDetails: {}", hullDetails);
        return hullDetails;
    }

    private int fetchMaxHull(String shipId, List<String> connectors) {
        int hull = getShipHull(shipId);
        log.debug("Base coreHull for shipId {}: {}", shipId, hull);
        for (String itemId : connectors) {
            CoreHull coreHull = coreHullService.get(itemId);
            if (!isNull(coreHull)) {
                hull += coreHull.getCapacity();
            }
        }

        return hull;
    }

    private int getShipHull(String shipId) {
        return Optional.ofNullable(shipService.get(shipId))
            .map(Ship::getCoreHull)
            .orElseThrow(() -> new RuntimeException("Ship not found with shipId " + shipId));
    }
}
