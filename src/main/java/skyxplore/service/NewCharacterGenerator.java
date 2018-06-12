package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.GameDataService;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.util.IdGenerator;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewCharacterGenerator {
    private static final Integer START_MONEY = 100000;

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
    private final IdGenerator idGenerator;

    public SkyXpCharacter createCharacter(String userId, String characterName){
        SkyXpCharacter character = new SkyXpCharacter();
        character.setCharacterId(idGenerator.getRandomId());
        character.setCharacterName(characterName);
        character.setUserId(userId);
        character.setMoney(START_MONEY);
        character.setEquipments(new ArrayList<>());
        return character;
    }

    public EquippedShip createShip(String characterId){
        EquippedShip ship = new EquippedShip();
        ship.setShipId(idGenerator.getRandomId());
        ship.setCharacterId(characterId);
        ship.setShipType(STARTER_SHIP_ID);
        Ship shipData = gameDataService.getShip(STARTER_SHIP_ID);
        ship.setConnectorSlot(shipData.getConnector());
        ship.setCoreHull(shipData.getCoreHull());
        fillWithConnectors(ship);
        return ship;
    }

    private void fillWithConnectors(EquippedShip ship) {
        ship.addConnector(GENERATOR_ID);
        ship.addConnector(GENERATOR_ID);
        ship.addConnector(BATTERY_ID);
        ship.addConnector(BATTERY_ID);
        ship.addConnector(STORAGE_ID);
        ship.addConnector(STORAGE_ID);
    }

    public EquippedSlot createDefenseSlot(String shipId){
        Ship shipData = gameDataService.getShip(STARTER_SHIP_ID);
        EquippedSlot slot = createSlot(shipId, shipData.getDefense());
        fillWithDefense(slot);
        return slot;
    }

    private EquippedSlot createSlot(String shipId, Slot slot) {
        EquippedSlot equippedSlot = new EquippedSlot();
        equippedSlot.setSlotId(idGenerator.getRandomId());
        equippedSlot.setShipId(shipId);
        equippedSlot.setFrontSlot(slot.getFront());
        equippedSlot.setLeftSlot(slot.getSide());
        equippedSlot.setRightSlot(slot.getSide());
        equippedSlot.setBackSlot(slot.getBack());
        return equippedSlot;
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

    public EquippedSlot createWeaponSlot(String shipId){
        Ship shipData = gameDataService.getShip(STARTER_SHIP_ID);
        EquippedSlot slot = createSlot(shipId, shipData.getDefense());
        fillWithWeapon(slot);
        return slot;
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
