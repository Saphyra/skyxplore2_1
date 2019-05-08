package com.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class UnequipConnectorService {
    private final EquippedShipDao equippedShipDao;
    private final EquipUtil equipUtil;
    private final UnequipExtenderService unequipExtenderService;

    void unequipConnector(String itemId, SkyXpCharacter character, EquippedShip ship) {
        ship.removeConnector(itemId);

        if (equipUtil.isExtender(itemId)) {
            log.info("Unequipping extender...");
            unequipExtenderService.unequipExtender(itemId, character, ship);
        }

        equippedShipDao.save(ship);
    }
}
