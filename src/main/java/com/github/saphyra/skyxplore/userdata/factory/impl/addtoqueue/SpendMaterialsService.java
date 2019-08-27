package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.userdata.factory.repository.FactoryDao;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
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
