package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.data.gamedata.domain.SlotType;
import com.github.saphyra.skyxplore.data.gamedata.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.ShopData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameDataFacade {
    private final GameDataQueryService gameDataQueryService;

    public GeneralDescription getData(String id) {
        return gameDataQueryService.getData(id);
    }

    public FactoryData getFactoryData(String elementId) {
        return gameDataQueryService.getFactoryData(elementId);
    }

    public ShopData findBuyable(String elementId) {
        return gameDataQueryService.findBuyable(elementId);
    }

    public EquipmentDescription findEquipmentDescription(String itemId) {
        return gameDataQueryService.findEquipmentDescription(itemId);
    }

    public boolean isUpgradable(String itemId) {
        return findEquipmentDescription(itemId).getLevel() < 3;
    }

    public List<EquipmentDescription> getEquipmentDescriptionBySlotAndLevel(SlotType slotType, int level) {
        return gameDataQueryService.getEquipmentDescriptionBySlotAndLevel(slotType, level);
    }
}
