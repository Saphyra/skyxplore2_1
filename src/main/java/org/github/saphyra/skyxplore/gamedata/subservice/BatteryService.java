package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Battery;

import javax.annotation.PostConstruct;

@Component
public class BatteryService extends AbstractGameDataService<Battery> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Battery.class);
    }
}
