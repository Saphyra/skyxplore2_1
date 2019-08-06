package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Generator;

import javax.annotation.PostConstruct;

@Component
public class GeneratorService extends AbstractGameDataService<Generator> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Generator.class);
    }
}
