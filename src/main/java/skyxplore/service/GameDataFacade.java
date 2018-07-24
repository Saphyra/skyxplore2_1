package skyxplore.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.entity.abstractentity.ShopData;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.service.gamedata.CategoryQueryService;
import skyxplore.service.gamedata.DataQueryService;
import skyxplore.service.gamedata.EquipmentDataCollectorService;

@SuppressWarnings("WeakerAccess")
@Service
@RequiredArgsConstructor
@Slf4j
//TODO explode
public class GameDataFacade {
    private final CategoryQueryService categoryQueryService;
    private final DataQueryService dataQueryService;
    private final EquipmentDataCollectorService equipmentDataCollectorService;

    public Map<String, GeneralDescription> collectEquipmentData(List<String> ids) {
        return equipmentDataCollectorService.collectEquipmentData(ids);
    }

    public Map<String, GeneralDescription> collectEquipmentData(EquippedShip ship, EquippedSlot defenseSlot, EquippedSlot weaponSlot) {
        return equipmentDataCollectorService.collectEquipmentData(ship, defenseSlot, weaponSlot);
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
