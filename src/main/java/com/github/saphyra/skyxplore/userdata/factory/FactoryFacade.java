package com.github.saphyra.skyxplore.userdata.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryFacade {
    private final FactoryCreatorService factoryCreatorService;

    public void createFactory(String characterId) {
        factoryCreatorService.createFactory(characterId);
    }
}
