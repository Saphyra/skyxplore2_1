package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.CoreHull;

import javax.annotation.PostConstruct;

@Component
public class CoreHullService extends AbstractGameDataService<CoreHull> {

    @Override
    @PostConstruct
    public void init() {
        super.load(CoreHull.class);
    }
}
