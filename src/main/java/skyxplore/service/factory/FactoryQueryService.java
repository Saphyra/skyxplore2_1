package skyxplore.service.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.exception.FactoryNotFoundException;
import skyxplore.service.character.CharacterQueryService;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryQueryService {
    private final CharacterQueryService characterQueryService;
    private final FactoryDao factoryDao;
    private final MaterialService materialService;

    public Factory findFactoryOfCharacterValidated(String characterId) {
        Factory factory = factoryDao.findByCharacterId(characterId);
        if (factory == null) {
            throw new FactoryNotFoundException("Factory not found for character " + characterId);
        }
        return factory;
    }

    public Map<String, MaterialView> getMaterials(String characterId) {
        characterQueryService.findByCharacterId(characterId);
        Factory factory = findFactoryOfCharacterValidated(characterId);
        Materials materials = factory.getMaterials();
        Map<String, MaterialView> result = fillWithMaterials(materials);
        log.info("Materials successfully queried for character {}", characterId);
        return result;
    }

    private Map<String, MaterialView> fillWithMaterials(Materials materials) {
        Map<String, MaterialView> result = new HashMap<>();
        materialService.keySet().forEach(
            key -> result.put(
                key,
                createMaterialView(key, materials.get(key))
            )
        );
        return result;
    }

    private MaterialView createMaterialView(String key, Integer amount) {
        Material material = materialService.get(key);
        return MaterialView.builder()
            .materialId(key)
            .name(material.getName())
            .description(material.getDescription())
            .amount(amount)
            .build();
    }

    public String getFactoryIdOfCharacter(String characterId) {
        return findFactoryOfCharacterValidated(characterId).getFactoryId();
    }
}
