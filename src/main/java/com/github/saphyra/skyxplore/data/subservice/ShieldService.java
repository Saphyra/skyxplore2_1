package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Shield;

import javax.annotation.PostConstruct;

@Component
public class ShieldService extends AbstractGameDataService<Shield> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Shield.class);
    }
}
