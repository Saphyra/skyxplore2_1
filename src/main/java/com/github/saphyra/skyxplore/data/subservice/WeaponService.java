package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Weapon;

import javax.annotation.PostConstruct;

@Component
public class WeaponService extends AbstractGameDataService<Weapon> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Weapon.class);
    }
}
