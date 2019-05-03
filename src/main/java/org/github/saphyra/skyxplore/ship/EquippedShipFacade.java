package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipFacade {
    static final String DEFENSE_SLOT_NAME = "defense";
    static final String WEAPON_SLOT_NAME = "weapon";
    static final String CONNECTOR_SLOT_NAME = "connector";

    static final String FRONT_SLOT_NAME = "front";
    static final String BACK_SLOT_NAME = "back";
    static final String LEFT_SLOT_NAME = "left";
    static final String RIGHT_SLOT_NAME = "right";

    private final EquipService equipService;
    private final EquipShipService equipShipService;
    private final ShipCreatorService shipCreatorService;
    private final ShipQueryService shipQueryService;
    private final UnequipService unequipService;

    //TODO unit test
    public void createShip(String characterId) {
        shipCreatorService.createShip(characterId);
    }

    void equip(EquipRequest request, String characterId) {
        equipService.equip(request, characterId);
    }

    void equipShip(String characterId, String shipId) {
        equipShipService.equipShip(characterId, shipId);
    }

    ShipView getShipData(String characterId) {
        return shipQueryService.getShipData(characterId);
    }

    public void unequip(UnequipRequest request, String characterId) {
        unequipService.unequip(request, characterId);
    }
}
