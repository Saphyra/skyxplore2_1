package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Extender;

public class ExtenderService extends AbstractGameDataService<Extender> {
    public ExtenderService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(Extender.class);
    }
}
