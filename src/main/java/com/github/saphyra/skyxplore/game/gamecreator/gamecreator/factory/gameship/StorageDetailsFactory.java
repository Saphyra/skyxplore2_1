package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.entity.Storage;
import com.github.saphyra.skyxplore.data.subservice.StorageService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.game.domain.ship.StorageDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageDetailsFactory {
    private final StorageService storageService;

    public StorageDetails create(ShipEquipments equipments) {
        int capacity = fetchCapacity(equipments.getConnectorEquipped());
        StorageDetails storageDetails = new StorageDetails(capacity);
        log.debug("Created StorageDetails: {}", storageDetails);
        return storageDetails;
    }

    private int fetchCapacity(List<String> items) {
        return items.stream()
            .filter(itemId -> !isNull(storageService.get(itemId)))
            .map(storageService::get)
            .mapToInt(Storage::getCapacity)
            .sum();
    }
}
