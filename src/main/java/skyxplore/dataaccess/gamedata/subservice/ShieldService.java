package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Shield;

public class ShieldService extends AbstractGameDataService<Shield> {
    public ShieldService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(Shield.class);
    }
}
