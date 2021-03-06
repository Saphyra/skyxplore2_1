package com.github.saphyra.skyxplore.userdata.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquipRequest;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
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
            equipExtenderService.equipExtender(request.getItemId(), ship);
        }

        ship.addConnector(request.getItemId());
        equippedShipDao.save(ship);
    }
}
