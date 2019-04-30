package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Shield;

import javax.annotation.PostConstruct;

@Component
public class ShieldService extends AbstractGameDataService<Shield> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Shield.class);
    }
}
