package com.github.saphyra.skyxplore.data;

import org.springframework.stereotype.Service;

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

    //TODO unit test
    public EquipmentDescription findEquipmentDescription(String itemId) {
        return dataQueryService.findEquipmentDescription(itemId);
    }
}
