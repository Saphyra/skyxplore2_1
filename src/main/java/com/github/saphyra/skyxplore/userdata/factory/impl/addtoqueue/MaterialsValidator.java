package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import org.springframework.stereotype.Component;

@Component
class MaterialsValidator {
    void validateMaterials(Materials materials, FactoryData elementData, int amount) {
        elementData.getMaterials().forEach((key, value) -> {
            int required = value * amount;
            Integer current = materials.get(key);
            if (current < required) {
                throw ExceptionFactory.notEnoughMaterials(key, amount, current);
            }
        });
    }
}
