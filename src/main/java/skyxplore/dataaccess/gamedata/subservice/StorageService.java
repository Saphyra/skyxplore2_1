package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Storage;

public class StorageService extends AbstractGameDataService<Storage> {
    public StorageService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.load(Storage.class);
    }
}
