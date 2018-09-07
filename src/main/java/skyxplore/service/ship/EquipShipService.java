package skyxplore.service.ship;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;

import skyxplore.exception.base.BadRequestException;
import skyxplore.service.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class EquipShipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final ShipService shipService;
    private final ShipQueryService shipQueryService;
    private final SlotDao slotDao;

    @Transactional
    public void equipShip(String characterId, String itemId) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        EquippedShip ship = shipQueryService.getShipByCharacterId(characterId);

        Ship shipToEquip = getShip(itemId);

        character.removeEquipment(itemId);
        character.addEquipment(ship.getShipType());

        updateShipConnectors(character, ship, shipToEquip);
        updateSlots(character, ship, shipToEquip);

        equippedShipDao.save(ship);
        characterDao.save(character);
    }

    private Ship getShip(String itemId) {
        Ship shipToEquip = shipService.get(itemId);
        if (shipToEquip == null) {
            throw new BadRequestException("Invalid ship id " + itemId);
        }
        return shipToEquip;
    }

    private void updateShipConnectors(SkyXpCharacter character, EquippedShip ship, Ship shipToEquip){
        ship.setShipType(shipToEquip.getId());
        ship.getConnectorEquipped().forEach(i -> {
            character.addEquipment(i);
            ship.removeConnector(i);
        });
        ship.setConnectorSlot(shipToEquip.getConnector());
    }

    private void updateSlots(SkyXpCharacter character, EquippedShip ship, Ship shipToEquip){
        EquippedSlot defenseSlot = slotDao.getById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotDao.getById(ship.getWeaponSlotId());
        List<EquippedSlot> slots = Arrays.asList(defenseSlot, weaponSlot);

        emptySlots(character, slots);

        defenseSlot.setFrontSlot(shipToEquip.getDefense().getFront());
        defenseSlot.setBackSlot(shipToEquip.getDefense().getBack());
        defenseSlot.setLeftSlot(shipToEquip.getDefense().getSide());
        defenseSlot.setRightSlot(shipToEquip.getDefense().getSide());

        weaponSlot.setFrontSlot(shipToEquip.getWeapon().getFront());
        weaponSlot.setBackSlot(shipToEquip.getWeapon().getBack());
        weaponSlot.setLeftSlot(shipToEquip.getWeapon().getSide());
        weaponSlot.setRightSlot(shipToEquip.getWeapon().getSide());

        slotDao.save(defenseSlot);
        slotDao.save(weaponSlot);
    }

    private void emptySlots(SkyXpCharacter character, List<EquippedSlot> slots){
        slots.forEach(slot -> {
            slot.getFrontEquipped().forEach(i -> {
                character.addEquipment(i);
                slot.removeFront(i);
            });
            slot.getBackEquipped().forEach(i -> {
                character.addEquipment(i);
                slot.removeBack(i);
            });
            slot.getLeftEquipped().forEach(i -> {
                character.addEquipment(i);
                slot.removeLeft(i);
            });
            slot.getRightEquipped().forEach(i -> {
                character.addEquipment(i);
                slot.removeRight(i);
            });
        });
    }
}
