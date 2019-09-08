package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Armor;

import javax.annotation.PostConstruct;

@Component
public class ArmorService extends AbstractDataService<Armor> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Armor.class);
    }
}
