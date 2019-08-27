package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Storage;

import javax.annotation.PostConstruct;

@Component
public class StorageService extends AbstractDataService<Storage> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Storage.class);
    }
}
