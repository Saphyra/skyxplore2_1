package org.github.saphyra.skyxplore.factory.impl.addtoqueue;

import org.github.saphyra.skyxplore.common.exception.NotEnoughMaterialsException;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
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
