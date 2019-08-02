package com.github.saphyra.skyxplore.data.subservice;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Ship;

@Component
public class ShipService extends AbstractGameDataService<Ship> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Ship.class);
    }

    //TODO unit test
    public List<Ship> getShipsByLevel(int level) {
        return values().stream()
            .filter(ship -> ship.getLevel() == level)
            .collect(Collectors.toList());
    }
}
