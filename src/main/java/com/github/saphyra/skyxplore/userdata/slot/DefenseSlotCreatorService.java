package com.github.saphyra.skyxplore.userdata.slot;

import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.data.entity.Slot;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import static com.github.saphyra.skyxplore.common.ShipConstants.ARMOR_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.SHIELD_ID;
import static com.github.saphyra.skyxplore.common.ShipConstants.STARTER_SHIP_ID;

@Service
@Slf4j
public class DefenseSlotCreatorService extends AbstractSlotCreator{
    private final ShipService shipService;

    public DefenseSlotCreatorService(
        ShipService shipService,
        IdGenerator idGenerator,
        SlotDao slotDao
    ) {
        super(idGenerator, slotDao);
        this.shipService = shipService;
    }

    @Override
    Slot getSlot() {
        return shipService.get(STARTER_SHIP_ID).getDefense();
    }

    void fillSlotWithEquipments(EquippedSlot defense) {
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
}
