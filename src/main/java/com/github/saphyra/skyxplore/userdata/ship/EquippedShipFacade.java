package com.github.saphyra.skyxplore.userdata.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipFacade {
    private final ShipCreatorService shipCreatorService;

    public void createShip(String characterId) {
        shipCreatorService.createShip(characterId);
    }
}
