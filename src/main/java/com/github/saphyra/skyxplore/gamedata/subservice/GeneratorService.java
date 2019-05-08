package com.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.gamedata.entity.Generator;

import javax.annotation.PostConstruct;

@Component
public class GeneratorService extends AbstractGameDataService<Generator> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Generator.class);
    }
}
