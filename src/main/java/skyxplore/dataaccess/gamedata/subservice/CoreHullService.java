package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.CoreHull;

public class CoreHullService extends AbstractGameDataService<CoreHull> {
    public CoreHullService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(CoreHull.class);
    }
}
