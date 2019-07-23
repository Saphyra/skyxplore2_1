package com.github.saphyra.skyxplore.data.subservice;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.entity.Material;

import javax.annotation.PostConstruct;

@Component
public class MaterialService extends AbstractGameDataService<Material> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Material.class);
    }
}
