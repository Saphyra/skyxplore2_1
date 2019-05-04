package org.github.saphyra.skyxplore.factory.impl.addtoqueue;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMaterialsException;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
//TODO unit test
class MaterialsValidator {
    void validateMaterials(Materials materials, FactoryData elementData, int amount) {
        Map<String, Integer> requiredMaterials = elementData.getMaterials();
        Set<String> keys = requiredMaterials.keySet();
        for (String key : keys) {
            int required = requiredMaterials.get(key) * amount;
            if (materials.get(key) < required) {
                throw new NotEnoughMaterialsException("Not enough " + key + ". Needed: " + required + ", have: " + materials.get(key));
            }
        }
    }
}
