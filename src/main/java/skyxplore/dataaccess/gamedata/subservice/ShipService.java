package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Ship;

public class ShipService extends AbstractGameDataService<Ship> {
    public ShipService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(Ship.class);
    }
}
