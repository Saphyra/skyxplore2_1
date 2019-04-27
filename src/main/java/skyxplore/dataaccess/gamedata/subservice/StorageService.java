package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Storage;

import javax.annotation.PostConstruct;

@Component
public class StorageService extends AbstractGameDataService<Storage> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Storage.class);
    }
}
