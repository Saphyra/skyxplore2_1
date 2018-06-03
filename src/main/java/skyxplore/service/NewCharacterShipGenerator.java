package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.GameDataService;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.EquippedSlot;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewCharacterShipGenerator {
    private static final String STARTER_SHIP_ID = "sta-01";

    private static final String GENERATOR_ID = "gen-01";
    private static final String BATTERY_ID = "bat-01";
    private static final String STORAGE_ID = "sto-01";

    private static final String SHIELD_ID = "shi-hclr-01";
    private static final String ARMOR_ID = "arm-01";

    private static final String LASER_ID = "las-mrldma-01";
    private static final String LAUNCHER_ID = "rla-hrldma-01";
    private static final String RIFLE_ID = "rif-lrldma-01";

    private final GameDataService gameDataService;

    public EquippedShip generateShip(Long characterId) {
        EquippedShip ship = createEmptyShip(characterId);
        fillWithConnectors(ship);
        fillWithDefense(ship.getDefenseSlot());
        fillWithWeapon(ship.getWeaponSlot());
        return ship;
    }

    private EquippedShip createEmptyShip(Long characterId) {
        EquippedShip ship = new EquippedShip();
        ship.setCharacterId(characterId);
        ship.setShipType(STARTER_SHIP_ID);
        Ship shipData = gameDataService.getShip(STARTER_SHIP_ID);
        ship.setConnectorSlot(shipData.getConnector());
        ship.setCoreHull(shipData.getCoreHull());
        ship.setDefenseSlot(createSlot(shipData.getDefense()));
        ship.setWeaponSlot(createSlot(shipData.getWeapon()));
        return ship;
    }

    private EquippedSlot createSlot(Slot slot) {
        EquippedSlot equippedSlot = new EquippedSlot();
        equippedSlot.setFrontSlot(slot.getFront());
        equippedSlot.setLeftSlot(slot.getSide());
        equippedSlot.setRightSlot(slot.getSide());
        equippedSlot.setBackSlot(slot.getBack());
        return equippedSlot;
    }

    private void fillWithConnectors(EquippedShip ship) {
        ship.addConnector(GENERATOR_ID);
        ship.addConnector(GENERATOR_ID);
        ship.addConnector(BATTERY_ID);
        ship.addConnector(BATTERY_ID);
        ship.addConnector(STORAGE_ID);
        ship.addConnector(STORAGE_ID);
    }

    private void fillWithDefense(EquippedSlot defense) {
        defense.addFront(SHIELD_ID);
        defense.addFront(SHIELD_ID);
        defense.addFront(ARMOR_ID);
        defense.addFront(ARMOR_ID);

        defense.addLeft(SHIELD_ID);
        defense.addLeft(ARMOR_ID);

        defense.addRight(SHIELD_ID);
        defense.addRight(ARMOR_ID);

        defense.addBack(SHIELD_ID);
        defense.addBack(ARMOR_ID);
    }

    private void fillWithWeapon(EquippedSlot weapon){
        weapon.addFront(LASER_ID);
        weapon.addFront(LASER_ID);
        weapon.addFront(LAUNCHER_ID);

        weapon.addLeft(RIFLE_ID);
        weapon.addRight(RIFLE_ID);
        weapon.addBack(RIFLE_ID);
    }
}
