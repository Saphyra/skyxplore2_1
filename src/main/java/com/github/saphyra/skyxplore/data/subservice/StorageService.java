package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Storage;

import javax.annotation.PostConstruct;

@Component
public class StorageService extends AbstractGameDataService<Storage> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Storage.class);
    }
}
