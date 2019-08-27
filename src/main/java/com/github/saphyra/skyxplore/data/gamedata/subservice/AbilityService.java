package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ability;

import javax.annotation.PostConstruct;

@Component
public class AbilityService extends AbstractDataService<Ability> {
    @Override
    @PostConstruct
    public void init() {
        super.load(Ability.class);
    }
}
