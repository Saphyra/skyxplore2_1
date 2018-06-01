package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Armor;

public class ArmorService extends AbstractGameDataService<Armor> {
    public ArmorService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(Armor.class);
    }
}
