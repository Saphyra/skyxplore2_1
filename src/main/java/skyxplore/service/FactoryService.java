package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.restcontroller.view.material.MaterialView;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryService {
    private final CharacterService characterService;
    private final FactoryDao factoryDao;
    private final MaterialService materialService;

    public Map<String, MaterialView> getMaterials(String characterId, String userId) {
        characterService.findCharacterByIdAuthorized(characterId, userId);
        Factory factory = factoryDao.findByCharacterId(characterId);
        if (factory == null) {
            //TODO throw exception - create common not found exception
        }
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
}
