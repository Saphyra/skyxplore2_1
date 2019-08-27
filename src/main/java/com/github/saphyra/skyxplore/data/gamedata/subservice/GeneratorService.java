package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Generator;

import javax.annotation.PostConstruct;

@Component
public class GeneratorService extends AbstractDataService<Generator> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Generator.class);
    }
}
