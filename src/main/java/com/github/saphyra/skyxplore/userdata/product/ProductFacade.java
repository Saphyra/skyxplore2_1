package com.github.saphyra.skyxplore.userdata.product;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductFactory productFactory;

    public void createAndSave(String factoryId, FactoryData elementData, Integer amount) {
        productFactory.createAndSave(factoryId, elementData, amount);
    }
}
