package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;

import javax.annotation.PostConstruct;

@Component
public class ShipService extends AbstractGameDataService<Ship> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Ship.class);
    }
}
