package com.github.saphyra.skyxplore.data.gamedata;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.SlotType;
import com.github.saphyra.skyxplore.data.gamedata.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.ShopData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class GameDataQueryService {
    private final List<AbstractDataService<? extends GeneralDescription>> dataServices;
    private final List<AbstractDataService<? extends EquipmentDescription>> equipmentDescriptionServices;

    GeneralDescription getData(String id) {
        return dataServices.stream()
            .filter(abstractDataService -> abstractDataService.containsKey(id))
            .findAny()
            .orElseThrow(() -> ExceptionFactory.equipmentNotFound(id))
            .get(id);
    }

    FactoryData getFactoryData(String elementId) {
        GeneralDescription element = getData(elementId);
        if (element instanceof FactoryData) {
            return (FactoryData) element;
        }
        throw new IllegalArgumentException(elementId + " is not instance of FactoryData");
    }

    ShopData findBuyable(String elementId) {
        GeneralDescription data = getData(elementId);
        if (data instanceof ShopData) {
            return (ShopData) data;
        }
        throw new IllegalArgumentException(elementId + " is not instance of ShopData.");
    }

    EquipmentDescription findEquipmentDescription(String itemId) {
        GeneralDescription data = getData(itemId);
        if (data instanceof EquipmentDescription) {
            return (EquipmentDescription) data;
        }
        throw new IllegalArgumentException(itemId + " is not instance of EquipmentDescription.");
    }

    List<EquipmentDescription> getEquipmentDescriptionBySlotAndLevel(SlotType slotType, int level) {
        return equipmentDescriptionServices.stream()
            .flatMap(service -> service.values().stream())
            .filter(equipmentDescription -> equipmentDescription.getSlot().equalsIgnoreCase(slotType.name()))
            .filter(equipmentDescription -> equipmentDescription.getLevel() == level)
            .collect(Collectors.toList());
    }
}
