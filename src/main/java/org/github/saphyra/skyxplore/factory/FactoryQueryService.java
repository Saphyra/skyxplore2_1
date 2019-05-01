package org.github.saphyra.skyxplore.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.common.exception.FactoryNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryQueryService {
    private final FactoryDao factoryDao;

    Factory findFactoryOfCharacterValidated(String characterId) {
        Factory factory = factoryDao.findByCharacterId(characterId);
        if (factory == null) {
            throw new FactoryNotFoundException("Factory not found for character " + characterId);
        }
        return factory;
    }

    public Materials getMaterials(String characterId) {
        return findFactoryOfCharacterValidated(characterId).getMaterials();
    }

    public String getFactoryIdOfCharacter(String characterId) {
        return findFactoryOfCharacterValidated(characterId).getFactoryId();
    }

    public Factory findByFactoryId(String factoryId) {
        return factoryDao.findById(factoryId)
            .orElseThrow(() -> new FactoryNotFoundException("Factory not found with factoryId " + factoryId));
    }
}
