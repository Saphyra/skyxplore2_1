package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Storage;

import javax.annotation.PostConstruct;

@Component
public class StorageService extends AbstractGameDataService<Storage> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Storage.class);
    }
}
