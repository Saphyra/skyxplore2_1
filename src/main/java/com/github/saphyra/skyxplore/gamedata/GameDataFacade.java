package com.github.saphyra.skyxplore.gamedata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.gamedata.entity.ShopData;
import org.springframework.stereotype.Service;

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

}
