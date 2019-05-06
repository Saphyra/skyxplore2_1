package org.github.saphyra.skyxplore.factory.impl.addtoqueue;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpendMaterialsService {
    private final FactoryDao factoryDao;

    void spendMaterials(Factory factory, FactoryData elementData, Integer amount) {
        Materials materials = factory.getMaterials();
        elementData.getMaterials().forEach((materialId, requiredAmount) ->
            materials.removeMaterial(materialId, requiredAmount * amount));
        factoryDao.save(factory);
    }
}
