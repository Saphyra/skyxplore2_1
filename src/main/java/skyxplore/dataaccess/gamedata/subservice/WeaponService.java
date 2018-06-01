package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Weapon;

public class WeaponService extends AbstractGameDataService<Weapon> {
    public WeaponService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(Weapon.class);
    }
}
