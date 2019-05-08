package com.github.saphyra.skyxplore.slot;

import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import com.github.saphyra.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.gamedata.entity.Slot;
import com.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import static com.github.saphyra.skyxplore.common.ShipConstants.LASER_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.LAUNCHER_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.RIFLE_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.STARTER_SHIP_ID;

@Service
@Slf4j
public class WeaponSlotCreatorService extends AbstractSlotCreator {
    private final ShipService shipService;

    public WeaponSlotCreatorService(
        ShipService shipService,
        IdGenerator idGenerator,
        SlotDao slotDao
    ) {
        super(idGenerator, slotDao);
        this.shipService = shipService;
    }

    @Override
    Slot getSlot() {
        return shipService.get(STARTER_SHIP_ID).getWeapon();
    }

    void fillSlotWithEquipments(EquippedSlot equippedSlot) {
        equippedSlot.addFront(LASER_ID);
        equippedSlot.addFront(LASER_ID);
        equippedSlot.addFront(LAUNCHER_ID);

        equippedSlot.addLeft(RIFLE_ID);
        equippedSlot.addRight(RIFLE_ID);
        equippedSlot.addBack(RIFLE_ID);
    }
}
