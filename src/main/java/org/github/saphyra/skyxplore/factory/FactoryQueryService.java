package org.github.saphyra.skyxplore.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.exception.FactoryNotFoundException;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryQueryService {
    private final FactoryDao factoryDao;

    public Factory findFactoryOfCharacterValidated(String characterId) {
        return factoryDao.findByCharacterId(characterId)
            .orElseThrow(() -> new FactoryNotFoundException("Factory not found for character " + characterId));
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
