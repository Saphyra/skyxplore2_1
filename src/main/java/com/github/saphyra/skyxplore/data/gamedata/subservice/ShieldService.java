package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Shield;

import javax.annotation.PostConstruct;

@Component
public class ShieldService extends AbstractDataService<Shield> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Shield.class);
    }
}
