package com.github.saphyra.skyxplore.product;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductFactory productFactory;

    public void createAndSave(String factoryId, FactoryData elementData, Integer amount) {
        productFactory.createAndSave(factoryId, elementData, amount);
    }
}
