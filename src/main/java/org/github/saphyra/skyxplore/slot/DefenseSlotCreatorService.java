package org.github.saphyra.skyxplore.slot;

import com.github.saphyra.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.gamedata.entity.Slot;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import static org.github.saphyra.skyxplore.common.ShipConstants.ARMOR_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.SHIELD_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.STARTER_SHIP_ID;

@Service
@Slf4j
//TODO unit test
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
