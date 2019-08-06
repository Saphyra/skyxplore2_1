package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import com.github.saphyra.skyxplore.userdata.slot.EquippedSlotFacade;
import org.springframework.stereotype.Service;

import static com.github.saphyra.skyxplore.common.ShipConstants.BATTERY_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.GENERATOR_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.STARTER_SHIP_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.STORAGE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
class ShipCreatorService {
    private final IdGenerator idGenerator;
    private final EquippedShipDao equippedShipDao;
    private final ShipService shipService;
    private final EquippedSlotFacade equippedSlotFacade;

    void createShip(String characterId) {
        Ship shipData = shipService.get(STARTER_SHIP_ID);

        String shipId = idGenerator.generateRandomId();
        EquippedShip ship = EquippedShip.builder()
            .characterId(characterId)
            .shipId(shipId)
            .shipType(STARTER_SHIP_ID)
            .coreHull(shipData.getCoreHull())
            .connectorSlot(shipData.getConnector())
            .defenseSlotId(equippedSlotFacade.createDefenseSlot(shipId))
            .weaponSlotId(equippedSlotFacade.createWeaponSlot(shipId))
            .build();
        fillWithConnectors(ship);
        log.info("Ship created: {}", ship);
        equippedShipDao.save(ship);
    }

    private void fillWithConnectors(EquippedShip ship) {
        ship.addConnector(GENERATOR_ID);
        ship.addConnector(GENERATOR_ID);
        ship.addConnector(BATTERY_ID);
        ship.addConnector(BATTERY_ID);
        ship.addConnector(STORAGE_ID);
        ship.addConnector(STORAGE_ID);
    }
}
