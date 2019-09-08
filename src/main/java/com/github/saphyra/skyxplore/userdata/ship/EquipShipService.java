package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class EquipShipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final ShipService shipService;
    private final ShipQueryService shipQueryService;
    private final SlotDao slotDao;
    private final SlotQueryService slotQueryService;

    @Transactional
    void equipShip(String characterId, String itemId) {
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        EquippedShip ship = shipQueryService.findShipByCharacterIdValidated(characterId);

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

    private void updateShipConnectors(SkyXpCharacter character, EquippedShip ship, Ship shipToEquip) {
        ship.setShipType(shipToEquip.getId());
        ship.getConnectorEquipped().forEach(i -> {
            character.addEquipment(i);
            ship.removeConnector(i);
        });
        ship.setConnectorSlot(shipToEquip.getConnector());
    }

    private void updateSlots(SkyXpCharacter character, EquippedShip ship, Ship shipToEquip) {
        EquippedSlot defenseSlot = slotQueryService.findSlotByIdValidated(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotQueryService.findSlotByIdValidated(ship.getWeaponSlotId());
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

    private void emptySlots(SkyXpCharacter character, List<EquippedSlot> slots) {
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
