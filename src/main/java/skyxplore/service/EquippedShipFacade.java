package skyxplore.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.EquipRequest;
import skyxplore.controller.request.UnequipRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.service.ship.EquipService;
import skyxplore.service.ship.EquipShipService;
import skyxplore.service.ship.ShipQueryService;
import skyxplore.service.ship.UnequipService;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipFacade {
    public static final String DEFENSE_SLOT_NAME = "defense";
    public static final String WEAPON_SLOT_NAME = "weapon";
    public static final String CONNECTOR_SLOT_NAME = "connector";

    public static final String FRONT_SLOT_NAME = "front";
    public static final String BACK_SLOT_NAME = "back";
    public static final String LEFT_SLOT_NAME = "left";
    public static final String RIGHT_SLOT_NAME = "right";

    private final EquipService equipService;
    private final EquipShipService equipShipService;
    private final ShipQueryService shipQueryService;
    private final UnequipService unequipService;

    public void equip(EquipRequest request, String userId, String characterId) {
        equipService.equip(request, userId, characterId);
    }

    public void equipShip(String userId, String characterId, String shipId) {
        equipShipService.equipShip(userId, characterId, shipId);
    }

    public View<ShipView> getShipData(String characterId, String userId) {
        return shipQueryService.getShipData(characterId, userId);
    }

    public void unequip(UnequipRequest request, String userId, String characterId) {
        unequipService.unequip(request, userId, characterId);
    }
}