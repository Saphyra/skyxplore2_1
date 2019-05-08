package com.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.gamedata.entity.Ability;

import javax.annotation.PostConstruct;

@Component
public class AbilityService extends AbstractGameDataService<Ability> {
    @Override
    @PostConstruct
    public void init() {
        super.load(Ability.class);
    }
}
