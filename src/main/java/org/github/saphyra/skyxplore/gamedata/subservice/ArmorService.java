package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Armor;

import javax.annotation.PostConstruct;

@Component
public class ArmorService extends AbstractGameDataService<Armor> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Armor.class);
    }
}