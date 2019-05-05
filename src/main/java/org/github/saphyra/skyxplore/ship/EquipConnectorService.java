package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class EquipConnectorService {
    private final EquippedShipDao equippedShipDao;
    private final EquipUtil equipUtil;
    private final EquipExtenderService equipExtenderService;

    void equipConnector(EquipRequest request, EquippedShip ship) {
        if (equipUtil.isExtender(request.getItemId())) {
            equipExtenderService.equipExtender(request, ship);
        }

        ship.addConnector(request.getItemId());
        equippedShipDao.save(ship);
    }
}
