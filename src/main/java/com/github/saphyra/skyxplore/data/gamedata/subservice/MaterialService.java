package com.github.saphyra.skyxplore.data.gamedata.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.Material;

import javax.annotation.PostConstruct;

@Component
public class MaterialService extends AbstractDataService<Material> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Material.class);
    }
}
