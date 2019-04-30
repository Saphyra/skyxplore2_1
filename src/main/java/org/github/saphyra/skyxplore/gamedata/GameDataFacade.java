package org.github.saphyra.skyxplore.gamedata;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.FactoryData;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.GeneralDescription;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.ShopData;

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
