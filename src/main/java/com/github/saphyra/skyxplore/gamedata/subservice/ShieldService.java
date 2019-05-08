package com.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.gamedata.entity.Shield;

import javax.annotation.PostConstruct;

@Component
public class ShieldService extends AbstractGameDataService<Shield> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Shield.class);
    }
}
