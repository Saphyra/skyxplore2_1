package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Battery;

import javax.annotation.PostConstruct;

@Component
public class BatteryService extends AbstractGameDataService<Battery> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Battery.class);
    }
}
