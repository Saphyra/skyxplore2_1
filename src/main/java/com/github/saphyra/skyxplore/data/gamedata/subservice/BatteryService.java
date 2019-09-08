package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Battery;

import javax.annotation.PostConstruct;

@Component
public class BatteryService extends AbstractDataService<Battery> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Battery.class);
    }
}
