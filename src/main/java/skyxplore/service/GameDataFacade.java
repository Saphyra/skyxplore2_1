package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.character.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.entity.abstractentity.ShopData;
import skyxplore.service.gamedata.CategoryQueryService;
import skyxplore.service.gamedata.DataQueryService;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Service
@RequiredArgsConstructor
@Slf4j
public class GameDataFacade {
    private final CategoryQueryService categoryQueryService;
    private final DataQueryService dataQueryService;

    public GeneralDescription getData(String id){
        return dataQueryService.getData(id);
    }

    public FactoryData getFactoryData(String elementId) {
        return dataQueryService.getFactoryData(elementId);
    }

    public Map<String, GeneralDescription> getElementsOfCategory(EquipmentCategoryRequest request) {
        return categoryQueryService.getElementsOfCategory(request);
    }

    public ShopData findBuyable(String elementId) {
        return dataQueryService.findBuyable(elementId);
    }

}
