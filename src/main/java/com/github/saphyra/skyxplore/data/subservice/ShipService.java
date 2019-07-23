package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Ship;

import javax.annotation.PostConstruct;

@Component
public class ShipService extends AbstractGameDataService<Ship> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Ship.class);
    }
}
