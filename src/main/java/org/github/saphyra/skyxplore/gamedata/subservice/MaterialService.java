package org.github.saphyra.skyxplore.gamedata.subservice;

import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.Material;

import javax.annotation.PostConstruct;

@Component
public class MaterialService extends AbstractGameDataService<Material> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Material.class);
    }
}
