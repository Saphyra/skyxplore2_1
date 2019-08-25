package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
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
