package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;

import javax.annotation.PostConstruct;

@Component
public class ExtenderService extends AbstractGameDataService<Extender> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Extender.class);
    }
}
