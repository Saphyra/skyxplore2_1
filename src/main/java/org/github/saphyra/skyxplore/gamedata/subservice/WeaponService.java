package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Weapon;

import javax.annotation.PostConstruct;

@Component
public class WeaponService extends AbstractGameDataService<Weapon> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Weapon.class);
    }
}
