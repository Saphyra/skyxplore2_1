package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Battery;

public class BatteryService extends AbstractGameDataService<Battery> {
    public BatteryService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.load(Battery.class);
    }
}
