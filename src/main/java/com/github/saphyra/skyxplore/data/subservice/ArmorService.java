package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Armor;

import javax.annotation.PostConstruct;

@Component
public class ArmorService extends AbstractGameDataService<Armor> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Armor.class);
    }
}
