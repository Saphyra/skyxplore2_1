package skyxplore.service.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.exception.FactoryNotFoundException;
import skyxplore.service.character.CharacterQueryService;

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

    public Map<String, MaterialView> getMaterials(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
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
                MaterialView.builder()
                    .materialId(key)
                    .name(materialService.get(key).getName())
                    .description(materialService.get(key).getDescription())
                    .amount(materials.get(key))
                    .build()
            )
        );
        return result;
    }

    public String getFactoryIdOfCharacter(String characterId) {
        return findFactoryOfCharacterValidated(characterId).getFactoryId();
    }
}
