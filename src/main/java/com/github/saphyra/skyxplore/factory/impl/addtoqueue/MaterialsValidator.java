package com.github.saphyra.skyxplore.factory.impl.addtoqueue;

import com.github.saphyra.skyxplore.common.exception.NotEnoughMaterialsException;
import com.github.saphyra.skyxplore.factory.domain.Materials;
import com.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.springframework.stereotype.Component;

@Component
class MaterialsValidator {
    void validateMaterials(Materials materials, FactoryData elementData, int amount) {
        elementData.getMaterials().forEach((key, value) -> {
            int required = value * amount;
            Integer current = materials.get(key);
            if (current < required) {
                throw new NotEnoughMaterialsException("Not enough " + key + ". Needed: " + required + ", have: " + current);
            }
        });
    }
}
