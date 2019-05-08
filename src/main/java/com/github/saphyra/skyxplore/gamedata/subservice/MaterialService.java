package com.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.gamedata.entity.Material;

import javax.annotation.PostConstruct;

@Component
public class MaterialService extends AbstractGameDataService<Material> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Material.class);
    }
}
