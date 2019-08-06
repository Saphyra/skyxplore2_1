package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Extender;

import javax.annotation.PostConstruct;

@Component
public class ExtenderService extends AbstractGameDataService<Extender> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Extender.class);
    }
}
