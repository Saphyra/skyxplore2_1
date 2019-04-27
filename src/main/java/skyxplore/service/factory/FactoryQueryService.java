package skyxplore.service.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.domain.factory.Factory;
import skyxplore.exception.FactoryNotFoundException;
import skyxplore.service.character.CharacterQueryService;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryQueryService {
    private final CharacterQueryService characterQueryService;
    private final FactoryDao factoryDao;

    Factory findFactoryOfCharacterValidated(String characterId) {
        Factory factory = factoryDao.findByCharacterId(characterId);
        if (factory == null) {
            throw new FactoryNotFoundException("Factory not found for character " + characterId);
        }
        return factory;
    }

    public Map<String, Integer> getMaterials(String characterId) {
        characterQueryService.findByCharacterId(characterId);
        Factory factory = findFactoryOfCharacterValidated(characterId);
        return factory.getMaterials();
    }

    public String getFactoryIdOfCharacter(String characterId) {
        return findFactoryOfCharacterValidated(characterId).getFactoryId();
    }

    public Factory findByFactoryId(String factoryId) {
        return factoryDao.findById(factoryId)
            .orElseThrow(() -> new FactoryNotFoundException("Factory not found with factoryId " + factoryId));
    }
}
