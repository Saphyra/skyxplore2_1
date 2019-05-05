package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class UnequipConnectorService {
    private final EquippedShipDao equippedShipDao;
    private final EquipUtil equipUtil;
    private final UnequipExtenderService unequipExtenderService;

    void unequipConnector(UnequipRequest request, SkyXpCharacter character, EquippedShip ship) {
        ship.removeConnector(request.getItemId());

        if (equipUtil.isExtender(request.getItemId())) {
            log.info("Unequipping extender...");
            unequipExtenderService.unequipExtender(request, character, ship);
        }

        equippedShipDao.save(ship);
    }
}
