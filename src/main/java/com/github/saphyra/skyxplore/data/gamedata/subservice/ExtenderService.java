package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Extender;

import javax.annotation.PostConstruct;

@Component
public class ExtenderService extends AbstractDataService<Extender> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Extender.class);
    }
}
