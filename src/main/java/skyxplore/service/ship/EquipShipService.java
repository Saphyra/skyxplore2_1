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
import skyxplore.exception.ShipNotFoundException;
import skyxplore.exception.base.BadRequestException;
import skyxplore.service.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquipShipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final ShipService shipService;
    private final SlotDao slotDao;

    @Transactional
    public void equipShip(String userId, String characterId, String shipId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(userId, characterId);
        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if (ship == null) {
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }

        Ship shipToEquip = shipService.get(shipId);
        if (shipToEquip == null) {
            throw new BadRequestException("Invalid ship id " + shipId);
        }

        character.removeEquipment(shipId);
        character.addEquipment(ship.getShipType());
        ship.setShipType(shipId);
        ship.getConnectorEquipped().forEach(i -> {
            character.addEquipment(i);
            ship.removeConnector(i);
        });
        ship.setConnectorSlot(shipToEquip.getConnector());

        EquippedSlot defenseSlot = slotDao.getById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotDao.getById(ship.getWeaponSlotId());
        List<EquippedSlot> slots = Arrays.asList(defenseSlot, weaponSlot);

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

        defenseSlot.setFrontSlot(shipToEquip.getDefense().getFront());
        defenseSlot.setBackSlot(shipToEquip.getDefense().getBack());
        defenseSlot.setLeftSlot(shipToEquip.getDefense().getSide());
        defenseSlot.setRightSlot(shipToEquip.getDefense().getSide());

        weaponSlot.setFrontSlot(shipToEquip.getWeapon().getFront());
        weaponSlot.setBackSlot(shipToEquip.getWeapon().getBack());
        weaponSlot.setLeftSlot(shipToEquip.getWeapon().getSide());
        weaponSlot.setRightSlot(shipToEquip.getWeapon().getSide());

        equippedShipDao.save(ship);
        characterDao.save(character);
        slotDao.save(defenseSlot);
        slotDao.save(weaponSlot);
    }
}
