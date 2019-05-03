package org.github.saphyra.skyxplore.factory;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterGeneratorConfig;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.gamedata.subservice.MaterialService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class FactoryCreatorService {
    private final CharacterGeneratorConfig config;
    private final FactoryDao factoryDao;
    private final IdGenerator idGenerator;
    private final MaterialService materialService;

    void createFactory(String characterId) {
        Factory factory = new Factory();
        factory.setFactoryId(idGenerator.generateRandomId());
        factory.setCharacterId(characterId);
        factory.setMaterials(createMaterials());
        log.info("Factory created: {}", factory);

        factoryDao.save(factory);
    }

    private Materials createMaterials() {
        Materials materials = new Materials();
        materialService.keySet().forEach(materialId -> materials.addMaterial(materialId, config.getStartMaterials()));
        return materials;
    }
}
