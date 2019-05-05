package org.github.saphyra.skyxplore.slot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquippedSlotFacade {
    private final DefenseSlotCreatorService defenseSlotCreatorService;
    private final WeaponSlotCreatorService weaponSlotCreatorService;

    public String createDefenseSlot(String shipId){
        return defenseSlotCreatorService.createSlot(shipId);
    }

    public String createWeaponSlot(String shipId){
        return weaponSlotCreatorService.createSlot(shipId);
    }
}
