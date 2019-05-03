package org.github.saphyra.skyxplore.ship;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.EquippedSlotFacade;
import org.springframework.stereotype.Service;

import static org.github.saphyra.skyxplore.common.ShipConstants.BATTERY_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.GENERATOR_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.STARTER_SHIP_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.STORAGE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ShipCreatorService {
    private final IdGenerator idGenerator;
    private final EquippedShipDao equippedShipDao;
    private final ShipService shipService;
    private final EquippedSlotFacade equippedSlotFacade;

    void createShip(String characterId) {
        //TODO ude builder
        EquippedShip ship = EquippedShip.builder().build();
        ship.setShipId(idGenerator.generateRandomId());
        ship.setCharacterId(characterId);
        ship.setShipType(STARTER_SHIP_ID);
        Ship shipData = shipService.get(STARTER_SHIP_ID);
        ship.setConnectorSlot(shipData.getConnector());
        ship.setCoreHull(shipData.getCoreHull());
        ship.setDefenseSlotId(equippedSlotFacade.createDefenseSlot(ship.getShipId()));
        ship.setWeaponSlotId(equippedSlotFacade.createWeaponSlot(ship.getShipId()));
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
