package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.materials.MaterialsConverter;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryService {
    private final CharacterService characterService;
    private final FactoryDao factoryDao;
    private final MaterialsConverter materialsConverter;

    public Materials getMaterials(String characterId, String userId){
        characterService.findCharacterByIdAuthorized(characterId, userId);
        Factory factory = factoryDao.findByCharacterId(characterId);
        if(factory == null){
            //TODO throw exception - create common not found exception
        }
        Materials materials = factory.getMaterials();
        Materials result =  materialsConverter.fillWithMaterials(materials);
        log.info("Materials successfully queried for character {}", characterId);
        return result;
    }
}
