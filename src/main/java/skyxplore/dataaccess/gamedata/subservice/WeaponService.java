package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Weapon;

import javax.annotation.PostConstruct;

@Component
public class WeaponService extends AbstractGameDataService<Weapon> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Weapon.class);
    }
}
