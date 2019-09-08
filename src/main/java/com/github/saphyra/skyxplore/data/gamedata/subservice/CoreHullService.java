package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.CoreHull;

import javax.annotation.PostConstruct;

@Component
public class CoreHullService extends AbstractDataService<CoreHull> {

    @Override
    @PostConstruct
    public void init() {
        super.load(CoreHull.class);
    }
}
