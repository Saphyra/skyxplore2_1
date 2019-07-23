package com.github.saphyra.skyxplore.userdata.factory;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.userdata.factory.repository.FactoryDao;
import com.github.saphyra.skyxplore.data.subservice.MaterialService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class FactoryCreatorService {
    private final FactoryConfig config;
    private final FactoryDao factoryDao;
    private final IdGenerator idGenerator;
    private final MaterialService materialService;

    void createFactory(String characterId) {
        Factory factory = Factory.builder()
            .factoryId(idGenerator.generateRandomId())
            .characterId(characterId)
            .materials(createMaterials())
            .build();
        log.info("Factory created: {}", factory);

        factoryDao.save(factory);
    }

    private Materials createMaterials() {
        Materials materials = new Materials();
        materialService.keySet().forEach(materialId -> materials.addMaterial(materialId, config.getStartMaterials()));
        return materials;
    }
}
