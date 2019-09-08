package com.github.saphyra.skyxplore.data.gamedata.subservice;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipService extends AbstractDataService<Ship> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Ship.class);
    }

    public List<Ship> getShipsByLevel(int level) {
        return values().stream()
            .filter(ship -> ship.getLevel().equals(level))
            .collect(Collectors.toList());
    }
}
