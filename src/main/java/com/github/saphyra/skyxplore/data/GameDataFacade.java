package com.github.saphyra.skyxplore.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
import com.github.saphyra.skyxplore.data.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.entity.ShopData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameDataFacade {
    private final DataQueryService dataQueryService;

    public GeneralDescription getData(String id) {
        return dataQueryService.getData(id);
    }

    public FactoryData getFactoryData(String elementId) {
        return dataQueryService.getFactoryData(elementId);
    }

    public ShopData findBuyable(String elementId) {
        return dataQueryService.findBuyable(elementId);
    }

    public EquipmentDescription findEquipmentDescription(String itemId) {
        return dataQueryService.findEquipmentDescription(itemId);
    }

    public boolean isUpgradable(String itemId) {
        return findEquipmentDescription(itemId).getLevel() < 3;
    }

    public List<EquipmentDescription> getEquipmentDescriptionBySlotAndLevel(SlotType slotType, int level) {
        return dataQueryService.getEquipmentDescriptionBySlotAndLevel(slotType, level);
    }
}
