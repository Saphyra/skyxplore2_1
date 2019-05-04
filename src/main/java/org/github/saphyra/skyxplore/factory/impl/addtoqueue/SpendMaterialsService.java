package org.github.saphyra.skyxplore.factory.impl.addtoqueue;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO unit test
public class SpendMaterialsService {
    private final FactoryDao factoryDao;

    void spendMaterials(Factory factory, FactoryData elementData, Integer amount) {
        Materials materials = factory.getMaterials();
        elementData.getMaterials().keySet()
            .forEach(materialId -> materials.removeMaterial(
                materialId,
                elementData.getMaterials().get(materialId) * amount)
            );

        factoryDao.save(factory);
    }
}
