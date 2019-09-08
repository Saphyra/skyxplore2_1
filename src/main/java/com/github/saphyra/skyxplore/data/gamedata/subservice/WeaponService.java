package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Weapon;

import javax.annotation.PostConstruct;

@Component
public class WeaponService extends AbstractDataService<Weapon> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Weapon.class);
    }
}
